package com.masaiproject.dto;

import com.masaiproject.validator.PhoneOrEmail;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpOtpDto {

	private String fullname;

	@PhoneOrEmail
	private String emailOrPhoneno;

	@Min(value = 6 , message = "Otp Should minimum six letter")
	@Max(value = 6, message = "Otp Should maximum six letter")
	private Integer otp;

}
