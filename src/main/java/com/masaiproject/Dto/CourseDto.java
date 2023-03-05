package com.masaiproject.dto;




import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.masaiproject.Enum.CourseDurationType;
import com.masaiproject.Enum.CourseType;
import com.masaiproject.Enum.Mode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
	
	private Integer id;
	
	@NotNull(message = "courseTitle cannot be Null")
	@NotBlank(message = "courseTitle cannot be blank")
	@NotEmpty(message = "courseTitle cannot be empty")
	@Size(min = 3,max = 30,message = "courseTitle size should be of 3-30")
     private String MainTitle;
	
	
	@NotNull(message = "courseTitle cannot be Null")
	@NotBlank(message = "courseTitle cannot be blank")
	@NotEmpty(message = "courseTitle cannot be empty")
	@Size(min = 3,max = 30,message = "courseTitle size should be of 3-30")
	private String subTitle;
	

	@NotNull(message = "paymentDetails cannot be Null")
	@NotBlank(message = "paymentDetails cannot be blank")
	@NotEmpty(message = "paymentDetails cannot be empty")
	
	private String courseDetails;
	
	
	@NotNull(message = "paymentDetails cannot be Null")
	@NotBlank(message = "paymentDetails cannot be blank")
	@NotEmpty(message = "paymentDetails cannot be empty")
	private String paymentDetailsText;

	
	private Integer courseDuration;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate startDate;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate lastDate;


	@NotNull(message = "courseType cannot be Null")
	@NotBlank(message = "courseType cannot be blank")
	@NotEmpty(message = "courseType cannot be empty")
	private CourseType courseType;
	
	@NotNull(message = "courseType cannot be Null")
	@NotBlank(message = "courseType cannot be blank")
	@NotEmpty(message = "courseType cannot be empty")
	
	private Mode mode;
		
	
	@NotNull(message = "courseType cannot be Null")
	@NotBlank(message = "courseType cannot be blank")
	@NotEmpty(message = "courseType cannot be empty")
	private CourseDurationType courseDurationType;
			

}

