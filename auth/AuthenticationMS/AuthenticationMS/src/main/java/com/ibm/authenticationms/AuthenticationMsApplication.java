package com.ibm.authenticationms;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.authenticationms.model.OrderRequest;
import com.ibm.authenticationms.service.OrderSubmitService;
import com.ibm.authenticationms.utility.JwtTokenUtil;


@SpringBootApplication
@EnableOAuth2Sso
@RestController
@EnableFeignClients
public class AuthenticationMsApplication extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	OrderSubmitService orderSubmitService;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationMsApplication.class, args);
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		System.out.println("I am at configure method");
        //Configuring Spring security access. For /login, /user, and /userinfo, we need authentication.
        //Logout is enabled.
        //Adding csrf token support to this configurer.
        http.authorizeRequests()
                .antMatchers("/login**", "/user","/userInfo").authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
 
    }
	
	
	
	@RequestMapping("/user")
    public Principal user(Principal principal) {
		System.out.println("I am at user method");
        //Principal holds the logged in user information.
        // Spring automatically populates this principal object after login.
        return principal;
    }
 
    @RequestMapping("/userInfo")
    public String userInfo(Principal principal){
    	System.out.println("I am at userInfo method");
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        final Authentication authentication = oAuth2Authentication.getUserAuthentication();
        //Manually getting the details from the authentication, and returning them as String.
        return authentication.getDetails().toString();
    }
    
   
    /**
     * 
     * @param itemno
     * @param quantity
     * @param principal
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String itemno, String quantity,Principal principal){
    	System.out.println("***** submit method start *****: "+itemno);
    	System.out.println("quantity: "+quantity);
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        final Authentication authentication = oAuth2Authentication.getUserAuthentication();
        System.out.println("*****User info ******* :"+authentication.getDetails().toString());
        
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("authenticationDetails", authentication.getDetails().toString());
        claims.put("itemCode", itemno);
        claims.put("quantity", quantity);
        
        
        //Create dummy Transaction Id
        String transactionId = "123457890";
        String transactionToken = jwtTokenUtil.doGenerateToken(claims, transactionId);
        
        System.out.println("JWT Transaction Token ***********:"+transactionToken);
        
        String userToken = generateUserToken(authentication);
        String serviceToken = generateServiceToken(authentication);
        
        OrderRequest request = new OrderRequest(transactionToken, userToken, serviceToken);
       
        String status = orderSubmitService.submitOrderDetails(request);
       
        System.out.println("Order Submit Status  :"+status);
    	
    	return "Order successfully submitted";
    }
    
    
    
    
	/**
	 * 
	 * @param authentication
	 * @return
	 */
	  private String generateUserToken(Authentication authentication) {
		  System.out.println("***** Generate  User Token *****: "+authentication.getName());
		  Map<String, Object> claims = new HashMap<>();
	      claims.put("authenticationDetails", authentication.getDetails().toString());
	  
	      return jwtTokenUtil.doGenerateToken(claims, authentication.getName());
	  
	  
	  }
	  
	 /**
	  * 
	  * @param authentication
	  * @return
	  */
	  private String generateServiceToken(Authentication authentication) {
		  System.out.println("***** Generate  Service Token *****: "+authentication.getName());
		  Map<String, Object> claims = new HashMap<>();
	      claims.put("authenticationDetails", authentication.getDetails().toString());
	  
	      return jwtTokenUtil.doGenerateToken(claims, authentication.getName());
	  
	  
	  }
	 
    
}
