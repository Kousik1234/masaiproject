package com.masaiproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccesDto {

	
	public Boolean succes;
	
	private String mssz;
	
	private String userId;
	
	private UserDto userDto;
	
	
	
}
