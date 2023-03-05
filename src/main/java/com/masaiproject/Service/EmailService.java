package com.masaiproject.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailService {
	
	
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	public void sendOtpMessage(String to, String subject, String text , String otp) throws MessagingException {
		
    
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);
	        message.setText(otp);
	        javaMailSender.send(message);
	        

  }


}