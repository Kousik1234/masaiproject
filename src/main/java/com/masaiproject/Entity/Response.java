package com.masaiproject.Entity;

import com.masaiproject.Enum.Status;
import com.masaiproject.dto.CourseResponseDto;
import com.masaiproject.dto.ScoreDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	
	private Status status;
	
	private CourseResponseDto courseResponceDto;
	
	private ScoreDto scoteDto;

}
