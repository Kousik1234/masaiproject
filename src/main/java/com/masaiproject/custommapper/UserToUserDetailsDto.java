package com.masaiproject.custommapper;

import org.springframework.core.convert.converter.Converter;

import com.masaiproject.Entity.Users;
import com.masaiproject.dto.UserDetailsDto;
import com.masaiproject.dto.UserDetailsDto.Notification;

public class UserToUserDetailsDto implements Converter<Users, UserDetailsDto> {

	@Override
	public UserDetailsDto convert(Users users) {

		UserDetailsDto userDetailsDto = new UserDetailsDto();

		userDetailsDto.setFullName(users.getFullName());
		userDetailsDto.setDob(users.getDob());
		userDetailsDto.setEmail(users.getEmail());
		userDetailsDto.setGender(users.getGender());
		userDetailsDto.setCompletedTwelthOn(users.getCompletedTwelthOn());
		userDetailsDto.setCurrentWorkingStatus(users.getCurrentWorkingStatus());
		userDetailsDto.setGraduatedOn(users.getGraduatedOn());
		userDetailsDto.setJobReady(users.getJobReady());
		userDetailsDto.setPhoneNo(users.getPhoneNo());
		userDetailsDto.setReferralCode(users.getReferralCode());

		Notification notification = new Notification();

		notification.setCalls(users.getCallsNotification());
		notification.setEmail(users.getEmailNotification());
		notification.setNewsletter(users.getNewsletterNotification());
		notification.setWhatsapp(users.getWhatsAppNotification());

		userDetailsDto.setNotification(notification);

		return userDetailsDto;
	}

}
