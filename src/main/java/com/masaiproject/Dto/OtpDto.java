package com.masaiproject.dto;

import com.masaiproject.validator.PhoneOrEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {

	
    private String fullname;
	
	@PhoneOrEmail
	private String emailOrPhoneno;
	
	
}
