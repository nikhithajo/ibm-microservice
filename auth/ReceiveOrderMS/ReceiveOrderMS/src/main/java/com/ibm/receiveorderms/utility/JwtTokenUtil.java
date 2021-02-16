package com.ibm.receiveorderms.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;


import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;

	
	private String secret = "secretval";

	//retrieve username from jwt token

	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);

	}


	/**
	 * 
	 * @param token
	 * @return
	 */

	public Date getExpirationDateFromToken(String token) {

		return getClaimFromToken(token, Claims::getExpiration);

	}

	/**
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);

	}

	

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

	}

//check if the token has expired

	private Boolean isTokenExpired(String token) {

		final Date expiration = getExpirationDateFromToken(token);

		return expiration.before(new Date());

	}

	
	

	
	public Claims getTransationToken(String token) {
		Claims claims = null;
		if (!isTokenExpired(token)) {

			System.out.println("I am at getTransationToken()");

			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			System.out.println("+++++++++++++++++++++++++++++++" + claims.get("itemCode"));

			/*
			 * Map<String, Object> expectedMap = new HashMap<String, Object>(); for
			 * (Entry<String, Object> entry : claims.entrySet()) {
			 * expectedMap.put(entry.getKey(), entry.getValue());
			 * System.out.println("expected key " + entry.getKey() + " expected value " +
			 * entry.getValue()); }
			 */

			

		}
		return claims;

	}
	
	
	
	
	
	
	
}
