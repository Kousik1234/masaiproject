package com.masaiproject.dto;



import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.masaiproject.Enum.AfterCompleteMasai;
import com.masaiproject.Enum.Gender;
import com.masaiproject.Enum.Graduate;
import com.masaiproject.Enum.TwelvePass;
import com.masaiproject.Enum.WorkingStatus;
import com.masaiproject.validator.ValidName;
import com.twilio.rest.api.v2010.account.call.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	
	@ValidName
	private String fullName;
	

    @Email(message = "Invalid email format")
	private String email;
	
	
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number format")
	private String phoneNo;
	
	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be in the past")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dob;
	
	private String referralCode;
	
	private Gender gender;
	
	@Min(value = 4, message = "graduatedate must be at least 4")
	@Max(value = 4, message = "graduatedate must be at least 4 ")
	private String graduatedOn;
	
	@Min(value = 4, message = "completedTwelthOn must be at least 4")
	@Max(value = 4, message = "completedTwelthOn must be at least 4 ")
	private String completedTwelthOn;
	
	private AfterCompleteMasai jobReady;
	
	private WorkingStatus currentWorkingStatus;
	
	
	private Notification notification;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Notification  {
			
			private Boolean whatsapp;
		    private Boolean email;
		    private Boolean calls;
		    private Boolean newsletter;
			
			
		}
	
	
}
