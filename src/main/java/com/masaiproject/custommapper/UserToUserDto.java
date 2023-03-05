package com.masaiproject.custommapper;

import org.springframework.core.convert.converter.Converter;

import com.masaiproject.Entity.Users;
import com.masaiproject.dto.UserDto;
import com.masaiproject.dto.UserDto.Notification;

public class UserToUserDto implements Converter<Users, UserDto>{

	@Override
	public UserDto convert(Users users) {
		
		UserDto userDto = new UserDto();
		
		userDto.setFullName(users.getFullName());
		userDto.setDob(users.getDob());
		userDto.setEmail(users.getEmail());
		userDto.setGender(users.getGender());
		userDto.setCompletedTwelthOn(users.getCompletedTwelthOn());
		userDto.setCurrentWorkingStatus(users.getCurrentWorkingStatus());
		userDto.setGraduatedOn(users.getGraduatedOn());
		userDto.setJobReady(users.getJobReady());
		userDto.setPhoneNo(users.getPhoneNo());
		userDto.setReferralCode(users.getReferralCode());
		
		Notification notification = new Notification();
		
		notification.setCalls(users.getCallsNotification());
		notification.setEmail(users.getEmailNotification());
		notification.setNewsletter(users.getNewsletterNotification());
		notification.setWhatsapp(users.getWhatsAppNotification());
		
		userDto.setNotification(notification);
		
		
		return userDto;
	}

}
