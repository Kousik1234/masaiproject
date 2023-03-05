package com.masaiproject.dto;

import java.time.LocalDate;

import com.masaiproject.Enum.AfterCompleteMasai;
import com.masaiproject.Enum.Gender;
import com.masaiproject.Enum.WorkingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

	private String fullName;

	private String email;

	private String phoneNo;

	private LocalDate dob;

	private String referralCode;

	private Gender gender;

	private String graduatedOn;

	private String completedTwelthOn;

	private AfterCompleteMasai jobReady;

	private WorkingStatus currentWorkingStatus;

	private Notification notification;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Notification {

		private Boolean whatsapp;
		private Boolean email;
		private Boolean calls;
		private Boolean newsletter;

	}

}
