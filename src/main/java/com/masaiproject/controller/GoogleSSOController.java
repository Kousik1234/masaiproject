package com.masaiproject.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masaiproject.Entity.Users;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.JwtService;
import com.masaiproject.dto.SuccesDto;



@RestController
@RequestMapping("/api/v2")
public class GoogleSSOController {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtService jwtService;

 @GetMapping("/")
  public ResponseEntity<String> WelcomeToGoogle() {
		
		   return ResponseEntity.ok("Welcome to google page");
	   }
 	
  
   
   @GetMapping("/googlelogin")
   public ResponseEntity<SuccesDto> user(Principal principal){
 	  
	   
	   SuccesDto succes = new SuccesDto();
	   
       Users user = new Users();
	   
	   
	   if (principal instanceof OAuth2AuthenticationToken) {
	        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;

	        // Get the user's name and email from the OAuth2 details
	        String name = oauth2Token.getPrincipal().getAttribute("name");
	        String email = oauth2Token.getPrincipal().getAttribute("email");

	        // Create a new User object with the user's name and email
	       user.setFullName(name);
	       user.setEmail(email);
	       
	       Optional<Users> user1 = userRepo.findByEmail(email);
	       
	       if(user1.isEmpty()) {
	       user = userRepo.save(user);
	       
	    	String auth = jwtService.generateToken(email);
	    	succes.setUserId(auth);
    		succes.setSucces(true);
    		succes.setMssz("SUCCESS");
	       
	       }
	       if(user1.isPresent()) {
	    	    String auth = jwtService.generateToken(email);
		    	succes.setUserId(auth);
	    		succes.setSucces(true);
	    		succes.setMssz("SUCCESS"); 
	       }
	        // Return the User object in a ResponseEntity with an OK status
	        return ResponseEntity.ok(succes);
	    }

	    // Return a ResponseEntity with a Forbidden status if the user is not authenticated
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

 
   }
  
}
