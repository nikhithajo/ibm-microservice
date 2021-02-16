package com.ibm.authenticationms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.authenticationms.model.OrderRequest;

@FeignClient(name = "receiveorderms",url = "http://localhost:9097")
public interface ReceiveOrderMs {
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public String submitOrderDetails(@RequestBody OrderRequest request);

}
