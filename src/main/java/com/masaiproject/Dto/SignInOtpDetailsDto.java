package com.masaiproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInOtpDetailsDto {

	private UserDetailsDto userDetailsDto;
	
	private Boolean loggedIn;
	private Boolean isProfileDetailPresent; 
	private String userId;

	
}
