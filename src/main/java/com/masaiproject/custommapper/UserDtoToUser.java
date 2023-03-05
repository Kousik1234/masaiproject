package com.masaiproject.custommapper;

import org.springframework.core.convert.converter.Converter;

import com.masaiproject.Entity.Users;
import com.masaiproject.dto.UserDto;
import com.masaiproject.dto.UserDto.Notification;

public class UserDtoToUser implements Converter<UserDto, Users>{

	@Override
	public Users convert(UserDto userDto) {
		
		Users user = new Users();
		
				
		 user.setFullName(userDto.getFullName());
		 user.setGender(userDto.getGender());
		 user.setDob(userDto.getDob());
		 user.setGraduatedOn(userDto.getGraduatedOn());
		 user.setCompletedTwelthOn(userDto.getCompletedTwelthOn());
		 user.setCurrentWorkingStatus(userDto.getCurrentWorkingStatus());
		 user.setPhoneNo(userDto.getPhoneNo());
		 user.setEmail(userDto.getEmail());
		 user.setJobReady(userDto.getJobReady());
		 user.setReferralCode(userDto.getReferralCode());
		 
		 Notification notification=userDto.getNotification();
		 
		 user.setCallsNotification(notification.getCalls());
		 user.setEmailNotification(notification.getEmail());
		 user.setNewsletterNotification(notification.getNewsletter());
		 user.setWhatsAppNotification(notification.getWhatsapp());
		
		 return user;
		
	}

}
