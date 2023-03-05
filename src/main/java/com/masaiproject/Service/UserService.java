package com.masaiproject.Service;


import com.masaiproject.Exception.JwtException;
import com.masaiproject.Exception.UserException;
import com.masaiproject.dto.SignInDetailsDto;
import com.masaiproject.dto.SignInDto;
import com.masaiproject.dto.SignUpDto;
import com.masaiproject.dto.TokenResultDto;
import com.masaiproject.dto.UpdateUserDetailsDto;
import com.masaiproject.dto.UserDto;
import com.masaiproject.dto.UserServiceDto;

public interface UserService {

	
    public UserServiceDto getdetailsuser(String userId) throws UserException,JwtException;
    
    public String updateuser(String userId,UserDto userDto) throws UserException,JwtException;
    
    public TokenResultDto signUpUser(SignUpDto sighUpDto) throws UserException;
	
	public SignInDetailsDto signInUser(SignInDto signInDto) throws UserException;
	
	
}
