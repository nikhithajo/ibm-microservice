package com.ibm.receiveorderms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.receiveorderms.model.OrderDetails;
import com.ibm.receiveorderms.model.OrderRequest;
import com.ibm.receiveorderms.model.ServiceToken;
import com.ibm.receiveorderms.model.TransactionToken;
import com.ibm.receiveorderms.model.UserToken;
import com.ibm.receiveorderms.repo.OrderDetailsRepository;
import com.ibm.receiveorderms.repo.ServiceTokenRepository;
import com.ibm.receiveorderms.repo.TransactionTokenRepository;
import com.ibm.receiveorderms.repo.UserTokenRepository;
import com.ibm.receiveorderms.utility.JwtTokenUtil;

import io.jsonwebtoken.Claims;

@Component
public class ReceiveOrderService {
	
	@Autowired
	JwtTokenUtil tokenUtil;
	
	@Autowired
	OrderDetailsRepository detailsRepository;
	
	@Autowired
	TransactionTokenRepository transactionTokenRepository;
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Autowired
	ServiceTokenRepository serviceTokenRepository;
	
	
	public String submitOrderDetails(@RequestBody OrderRequest request) {
		
		Claims claims = tokenUtil.getTransationToken(request.getTransactionToken());
		
		//Save Order details 
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setItemCode(claims.get("itemCode").toString());
		orderDetails.setQuantity(claims.get("quantity").toString());
		
		detailsRepository.save(orderDetails);
		
		//Save Transaction details 
		
		TransactionToken  transactionToken = new TransactionToken();
		transactionToken.setToken(request.getTransactionToken());
		transactionTokenRepository.save(transactionToken);
		
		//Save User token 
		UserToken userToken = new UserToken();
		userToken.setToken(request.getUserToken());
		userTokenRepository.save(userToken);
		
		
		//Save Service token
		ServiceToken serviceToken = new ServiceToken();
		serviceToken.setToken(request.getServiceToken());
		serviceTokenRepository.save(serviceToken);
		
		return "success";
	}

}
