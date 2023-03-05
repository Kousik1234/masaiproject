package com.masaiproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceDto {

	
	private Boolean isProfileDetailPresent;
	private Boolean loggedIn;
	
	private UserDto userDto;
	
}
