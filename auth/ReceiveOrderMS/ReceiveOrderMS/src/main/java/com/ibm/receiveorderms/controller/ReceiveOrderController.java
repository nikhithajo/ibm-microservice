/**
 * 
 */
package com.ibm.receiveorderms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.receiveorderms.model.OrderRequest;
import com.ibm.receiveorderms.service.ReceiveOrderService;



@RestController
public class ReceiveOrderController {
	
	@Autowired
	ReceiveOrderService receiveOrderService;
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public String submitOrderDetails(@RequestBody OrderRequest request) {
		System.out.println(" ********Submit Order Details ******** ");
		receiveOrderService.submitOrderDetails(request);
		return "Success";
	}

}
