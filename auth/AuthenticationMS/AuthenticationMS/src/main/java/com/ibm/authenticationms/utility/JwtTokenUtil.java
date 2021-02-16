/**
 * 
 */
package com.ibm.authenticationms.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;

	
	private String secret = "secretval";

//retrieve username from jwt token

	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);

	}

//retrieve expiration date from jwt token

	public Date getExpirationDateFromToken(String token) {

		return getClaimFromToken(token, Claims::getExpiration);

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);

	}

	// for retrieveing any information from token we will need the secret key

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

	}

//check if the token has expired

	private Boolean isTokenExpired(String token) {

		final Date expiration = getExpirationDateFromToken(token);

		return expiration.before(new Date());

	}

//generate token for user

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();

		return doGenerateToken(claims, userDetails.getUsername());

	}

 

	public String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))

				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}

//validate token

	public Boolean validateToken(String token, UserDetails userDetails) {

		final String username = getUsernameFromToken(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}
	
	
	// getTransationToken 

	
	public void getTransationToken(String token) {
		
		if (!isTokenExpired(token)) {

			System.out.println("I am at getTransationToken()");

			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			System.out.println("+++++++++++++++++++++++++++++++" + claims.get("itemCode"));

			Map<String, Object> expectedMap = new HashMap<String, Object>();
			for (Entry<String, Object> entry : claims.entrySet()) {
				expectedMap.put(entry.getKey(), entry.getValue());
				System.out.println("expected key " + entry.getKey() + " expected value " + entry.getValue());
			}

			System.out.println("**********************" + expectedMap.get("itemCode"));
			System.out.println("**********************" + expectedMap.get("quantity"));

		}

	}
	
	
	
	
	
	
	
}
