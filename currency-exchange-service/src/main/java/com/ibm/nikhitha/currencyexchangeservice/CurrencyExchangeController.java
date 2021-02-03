package com.ibm.nikhitha.currencyexchangeservice;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	//get conversion factor
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue
		(@PathVariable String from, @PathVariable String to){
		
	//	ExchangeValue exchangeValue = new  ExchangeValue(1000l,from,to,BigDecimal.valueOf(65));
		ExchangeValue exchangeValue = 
				repository.findByFromAndTo(from, to);
		//this is to create multiple instance and check ribbon
		if(null!=exchangeValue)
		{
			exchangeValue.setPort(
		  Integer.parseInt(environment.getProperty("local.server.port")));
		}
		else
			throw new IllegalArgumentException("Data not found ");
			
		  logger.info("{}", exchangeValue);
		return exchangeValue;
	}
	
	//add conversion factor
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	@PostMapping(value = "/currency-exchange/create/conversionfactor", consumes = "application/json", produces = "application/json")
	public String createCSonversionfactor(@RequestBody ExchangeValue conFactor) {

		if(null!= conFactor)
		{	try {
				repository.save(conFactor);
			} catch (DataAccessException e) {
				// TODO: handle exception
				 logger.info("{}", "save not success");
			}
		}
		else 
			throw new IllegalArgumentException("Request body is null");
		return "created";

	};
	
	
	// update conversion factor
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	@PutMapping(value = "/currency-exchange/update/conversionfactor", consumes = "application/json", produces = "application/json")
	public String updateConversionfactor(@RequestBody ExchangeValue conFactor) {

		ExchangeValue exchangeValue = repository.findByFromAndTo(conFactor.getFrom(), conFactor.getTo());
		if (null != exchangeValue) {
			exchangeValue.setConversionMultiple(conFactor.getConversionMultiple());
			repository.save(conFactor);
		} else
			throw new IllegalArgumentException("Data not found to update conversion factor");
		return "updated";

	};
	
	
	public String fallbackRetrieveConfiguration(ExchangeValue conFactor,Throwable e ) {
		return e.getMessage();
	}


	
}
