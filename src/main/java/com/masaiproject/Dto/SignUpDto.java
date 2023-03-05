package com.masaiproject.dto;

import com.masaiproject.validator.PhoneOrEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String fullname;
	
	@PhoneOrEmail
	private String emailOrPhoneno;
	
	private String password;
	
	
}
