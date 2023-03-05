package com.masaiproject.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.hibernate.tool.schema.internal.ExceptionHandlerHaltImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masaiproject.Entity.OtpEntity;
import com.masaiproject.Entity.Users;
import com.masaiproject.Exception.OtpException;
import com.masaiproject.Repositry.OtpRepo;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.EmailService;
import com.masaiproject.Service.JwtService;
import com.masaiproject.Service.OTPService;
import com.masaiproject.Service.OtpSendService;
import com.masaiproject.custommapper.UserToUserDto;
import com.masaiproject.dto.MessageDto;
import com.masaiproject.dto.OtpDto;
import com.masaiproject.dto.SignInDetailsDto;
import com.masaiproject.dto.SignInOtpDetailsDto;
import com.masaiproject.dto.SignInOtpDto;
import com.masaiproject.dto.SignUpDetailsDto;
import com.masaiproject.dto.SignUpOtpDto;
import com.masaiproject.dto.SuccesDto;
import com.masaiproject.dto.UserDto;
import com.masaiproject.limiter.RateLimiter;

@RestController
@RequestMapping("/api/v1")
public class OTPController {

	@Autowired
	public OTPService otpService;

	@Autowired
	public EmailService emailService;

	@Autowired
	public OtpRepo otpRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserToUserDto userToUserDto;

	@Autowired
	private OtpSendService otpSendService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RateLimiter rateLimiter;

	private final String TOPIC_DESTINATION = "/topic/sms";

	private boolean isValidEmail(String input) {
		String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return input.matches(emailPattern);
	}

	private boolean isValidPhone(String input) {
		String phonePattern = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";
		return input.matches(phonePattern);
	}

	@PostMapping("/otp/send/email")
	public ResponseEntity<?> generateOTP(@RequestParam("email") String email) throws MessagingException, OtpException {

		final String SEND = "Otp Send Succesfully";

		MessageDto messegeDto = new MessageDto();

		if (!rateLimiter.allow(email)) {
			return new ResponseEntity<String>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);
		}
		try {
			messegeDto = otpSendService.SendOtpEmail(email);

			return new ResponseEntity<MessageDto>(messegeDto, HttpStatus.OK);
		} catch (Exception e) {

			messegeDto.setMessage("Failed to send OTP");
			return new ResponseEntity<MessageDto>(messegeDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/otp/send/phoneno")
	public ResponseEntity<?> smsSubmit(@Valid @RequestParam("phoneno") String phoneno)
			throws ParseException, OtpException {

		MessageDto messegeDto = new MessageDto();

		if (!rateLimiter.allow(phoneno)) {
			return new ResponseEntity<String>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);
		}
		try {
			messegeDto = otpSendService.SendOtpPhoneNo(phoneno);

			return new ResponseEntity<MessageDto>(messegeDto, HttpStatus.OK);
		} catch (Exception e) {
			messegeDto.setMessage("Failed to send OTP");
			return new ResponseEntity<MessageDto>(messegeDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/otp/signup/send/email/phoneno")
	public ResponseEntity<String> emailgenerateOTP(@RequestBody OtpDto otpDto)
			throws MessagingException, ParseException, OtpException {

		final String SEND = "Otp Send Succesfully";

		String emailorphoneno = otpDto.getEmailOrPhoneno();

		if (!rateLimiter.allow(emailorphoneno)) {
			return new ResponseEntity<String>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);
		}

		if (isValidEmail(emailorphoneno)) {

			otpSendService.SendOtpEmail(emailorphoneno);

		} else if (isValidPhone(emailorphoneno)) {
			otpSendService.SendOtpPhoneNo(emailorphoneno);
		}

		return new ResponseEntity<String>("SEND", HttpStatus.OK);
	}

	@GetMapping(value = "/validateOtp/otp", produces = { "application/xml" })
	public ResponseEntity<?> validateOtp(@RequestParam("otpnum") int otpnum) throws OtpException {

		// if(otpnum == 6) {
		try {

			SuccesDto succesDto = otpSendService.validOtp(otpnum);
			return new ResponseEntity<SuccesDto>(succesDto, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.getMessage();
			throw new OtpException("enter a valid otp");
		}
		// }else {
		// throw new OtpException("enter a six digit otp");
		// }

	}

	@PostMapping("/signup/emailorphone/otp")
	public ResponseEntity<?> SignupwithOtp(@RequestBody SignUpOtpDto signUpOtpDto) throws OtpException {

		SignUpDetailsDto signUpDetailsDto = new SignUpDetailsDto();

		try {

			signUpDetailsDto = otpSendService.SignUpUsingOtp(signUpOtpDto);
			return new ResponseEntity<SignUpDetailsDto>(signUpDetailsDto, HttpStatus.OK);

		} catch (OtpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/signin/emailorphone/otp")
	public ResponseEntity<?> signinwithotp(@RequestBody SignInOtpDto signInOtpDto) throws OtpException {
		
		MessageDto messageDto = new MessageDto();
		
		try {
			SignInOtpDetailsDto signInOtpDetailsDto = otpSendService.SignInUsingOtp(signInOtpDto);
			return new ResponseEntity<SignInOtpDetailsDto>(signInOtpDetailsDto,HttpStatus.OK);
		}catch(OtpException e) {
			messageDto.setMessage(e.getMessage());
			return new ResponseEntity<MessageDto>(messageDto,HttpStatus.BAD_REQUEST);
		}catch (Exception e){
			messageDto.setMessage(e.getMessage());
			return new ResponseEntity<>(messageDto , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
