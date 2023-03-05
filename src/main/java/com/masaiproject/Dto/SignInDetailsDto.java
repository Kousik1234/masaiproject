package com.masaiproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDetailsDto {

	private UserDto userDto;
	
	private String userId;
	
	private String messege;
	
	
}
