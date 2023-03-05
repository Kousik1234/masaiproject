package com.masaiproject.controller;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Objects;
import com.masaiproject.Entity.Users;
import com.masaiproject.Exception.JwtException;
import com.masaiproject.Exception.UserException;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.JwtService;
import com.masaiproject.Service.UserService;
import com.masaiproject.dto.SignInDetailsDto;
import com.masaiproject.dto.SignInDto;
import com.masaiproject.dto.SignUpDto;
import com.masaiproject.dto.TokenResultDto;
import com.masaiproject.dto.UpdateUserDetailsDto;
import com.masaiproject.dto.UserDto;
import com.masaiproject.dto.UserServiceDto;


@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = {"Authorization", "Content-Type"})
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    private boolean isValidEmail(String input) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailPattern);
    }
	
	private boolean isValidPhone(String input) {
        String phonePattern = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        return input.matches(phonePattern);
    }
	

	
	@PostMapping("/signup/email/password")
	public ResponseEntity<TokenResultDto> registeruserHandeller1(@Valid @RequestBody SignUpDto signUpDto) throws UserException {
	    TokenResultDto user = userService.signUpUser(signUpDto);
	    return ResponseEntity.ok().body(user);
	}

	
	
	@GetMapping("/signin/email/password")
	public ResponseEntity<SignInDetailsDto> authenticateAndGetToken(@RequestBody SignInDto signInDto) {
        
		SignInDetailsDto signInDetailsDto = new SignInDetailsDto();
		
       try {
		 signInDetailsDto = userService.signInUser(signInDto);
		return new ResponseEntity<SignInDetailsDto>(signInDetailsDto,HttpStatus.OK);
	} catch (UserException e) {
		// TODO Auto-generated catch block
		return new ResponseEntity<SignInDetailsDto>(signInDetailsDto,HttpStatus.BAD_REQUEST);
	}

    }
    
    
    @GetMapping("/get")
	public String get() {
		return "kousik";
	}
    
     
      
	
	 @GetMapping("/user/details/authtoken")
     public Optional<Users> getCurrentUser(@RequestHeader("Authorization") String authToken) {
       String token = authToken.substring(7); // Remove the "Bearer " prefix
       
       String username = jwtService.extractUsername(token);
       
     //  String name = jwtService.
       if(isValidEmail(username)) {
       Optional<Users> user = repo.findByEmail(username);
       return user;
       }else {
    	   Optional<Users> user = repo.findByPhoneNo(username); 
    	   return user;
       }
       
       
     }

	 @GetMapping("/getUserDetails")
	 public ResponseEntity<?> getcurrentuser(@RequestParam("userId") String userId) {
		 
		
	    
		UserServiceDto userServiceDto = new UserServiceDto();
		try {
			if(userId==null && StringUtils.isEmpty(userId)) {
				userServiceDto.setIsProfileDetailPresent(false);
				userServiceDto.setLoggedIn(false);
				return new ResponseEntity<UserServiceDto>(userServiceDto,HttpStatus.BAD_REQUEST);
			}
			userServiceDto = userService.getdetailsuser(userId);
			return new ResponseEntity<>(userServiceDto,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			userServiceDto.setIsProfileDetailPresent(false);
			userServiceDto.setLoggedIn(false);
			return new ResponseEntity<UserServiceDto>(userServiceDto,HttpStatus.BAD_REQUEST);
		} 
		  
		 
		 
	 }

	 
	 
	 @PutMapping("/fillUserDetails")
	 public ResponseEntity<String> Updatecurrentuser(@RequestParam("userId") String userId,@RequestBody UserDto userDto) {
	     
		 String exception = "token invalid"; 
		 
		 
		 
		 try {
			String msz = userService.updateuser(userId, userDto);
			return new ResponseEntity<String>(msz,HttpStatus.OK);
		} catch (JwtException | UserException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(exception,HttpStatus.UNAUTHORIZED);
		}
		 
		 
	 }

	
	
}
