package com.ibm.receiveorderms.model;

public class OrderRequest {
	
	
	public String transactionToken;
	public String userToken;
	public String serviceToken;
	
	
	
	public OrderRequest(String transactionToken, String userToken, String serviceToken) {
		super();
		this.transactionToken = transactionToken;
		this.userToken = userToken;
		this.serviceToken = serviceToken;
	}
	/**
	 * @return the transactionToken
	 */
	public String getTransactionToken() {
		return transactionToken;
	}
	/**
	 * @param transactionToken the transactionToken to set
	 */
	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}
	/**
	 * @return the userToken
	 */
	public String getUserToken() {
		return userToken;
	}
	/**
	 * @param userToken the userToken to set
	 */
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	/**
	 * @return the serviceToken
	 */
	public String getServiceToken() {
		return serviceToken;
	}
	/**
	 * @param serviceToken the serviceToken to set
	 */
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}
	

}



