package com.masaiproject.Service;

import com.masaiproject.Exception.OtpException;
import com.masaiproject.dto.MessageDto;
import com.masaiproject.dto.SignInOtpDetailsDto;
import com.masaiproject.dto.SignInOtpDto;
import com.masaiproject.dto.SignUpDetailsDto;
import com.masaiproject.dto.SignUpOtpDto;
import com.masaiproject.dto.SuccesDto;

public interface OtpSendService {

	public MessageDto SendOtpEmail(String email)throws OtpException;
	
	public MessageDto SendOtpPhoneNo(String phoneno)throws OtpException;
	
	public SignUpDetailsDto SignUpUsingOtp(SignUpOtpDto signUpOtpDto)throws OtpException;
	
	//public SignUpDetailsDto SignUpPhoneOtp(SignUpOtpDto signUpOtpDto)throws OtpException;
	
	public SignInOtpDetailsDto SignInUsingOtp(SignInOtpDto signInOtpDto)throws OtpException;
	
	public SuccesDto validOtp(Integer otpnum)throws OtpException;
	

}
