package com.ibm.authenticationms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.authenticationms.model.OrderRequest;

@Component
public class OrderSubmitService {
	
	@Autowired
	ReceiveOrderMs  receiveOrderMs;
	
	
	public String submitOrderDetails(OrderRequest request) {
		String response = "failed";
		response = receiveOrderMs.submitOrderDetails(request);
		
		return response;
	}
	

}
