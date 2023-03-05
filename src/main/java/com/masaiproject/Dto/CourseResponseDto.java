package com.masaiproject.dto;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
	
	
	
	private String MainTitle;
		
	private String subTitle;
	
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate startDate;
	
	
	

}

