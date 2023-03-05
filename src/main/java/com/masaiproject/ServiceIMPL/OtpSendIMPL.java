package com.masaiproject.ServiceIMPL;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.masaiproject.Entity.OtpEntity;
import com.masaiproject.Entity.Users;
import com.masaiproject.Exception.OtpException;
import com.masaiproject.Repositry.OtpRepo;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.EmailService;
import com.masaiproject.Service.JwtService;
import com.masaiproject.Service.OTPService;
import com.masaiproject.Service.OtpSendService;
import com.masaiproject.custommapper.UserToUserDetailsDto;
import com.masaiproject.custommapper.UserToUserDto;
import com.masaiproject.dto.MessageDto;
import com.masaiproject.dto.SignInOtpDetailsDto;
import com.masaiproject.dto.SignInOtpDto;
import com.masaiproject.dto.SignUpDetailsDto;
import com.masaiproject.dto.SignUpOtpDto;
import com.masaiproject.dto.SuccesDto;
import com.masaiproject.dto.UserDetailsDto;
import com.masaiproject.dto.UserDto;
import com.masaiproject.limiter.RateLimiter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.MessagingException;

@Service
public class OtpSendIMPL implements OtpSendService {

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
	private UserToUserDetailsDto userToUserDetailsDto;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RateLimiter rateLimiter;

	private boolean isValidEmail(String input) {
		String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return input.matches(emailPattern);
	}

	private boolean isValidPhone(String input) {
		String phonePattern = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";
		return input.matches(phonePattern);
	}

	private final String ACCOUNT_SID = "AC3c737e570b8d366c13f9c6db33275a8b";

	private final String AUTH_TOKEN = "b5cbce998fba57b77d639582a44dc6c6";

	private final String FROM_NUMBER = "+17274781247";

	@Override
	public MessageDto SendOtpEmail(String email) {

		final String SEND = "Otp Send Succesfully";

		MessageDto messageDto = new MessageDto();
		OtpEntity otpentity = new OtpEntity();

		try {

			if (isValidEmail(email)) {

				int otp = otpService.generateOTP();

				Optional<OtpEntity> dbotp = otpRepo.findByUsername(email);
				
	
				if (dbotp.isPresent()) {
					
					otpentity = dbotp.get();
				
					otpRepo.delete(otpentity);
					
					otpentity.setOtp(otp);

					otpentity.setOtpExpiry(LocalDateTime.now().plusMinutes(30));
					otpentity.setUsername(email);

					otpRepo.save(otpentity);

					String otp1 = String.valueOf(otp);

					emailService.sendOtpMessage(email, "OTP -SpringBoot", "kousik", otp1);

					messageDto.setMessage(SEND);

					return messageDto;
				} else {

					// otpRepo.updateOtpByUsername(email, otp);
	

					otpentity.setOtp(otp);
					otpentity.setUsername(email);

					otpentity.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

					otpRepo.save(otpentity);

					String otp1 = String.valueOf(otp);

					emailService.sendOtpMessage(email, "OTP -SpringBoot", "kousik", otp1);

					messageDto.setMessage(SEND);

					return messageDto;
				}
			} else {
				messageDto.setMessage("Invalid Email");
				return messageDto;
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			messageDto.setMessage("Failed to send OTP");
			return messageDto;
		}

	}

	@Override
	public MessageDto SendOtpPhoneNo(String phoneno) {

		final String SEND = "Otp Send Succesfully";

		MessageDto messageDto = new MessageDto();
		OtpEntity otpentity = new OtpEntity();

		try {

			if (isValidPhone(phoneno)) {

				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

				int sendotp = otpService.generateOTP();

				Optional<OtpEntity> dbotp = otpRepo.findByUsername(phoneno);

				

				if (dbotp.isEmpty()) {

					otpentity.setOtp(sendotp);

					otpentity.setOtpExpiry(LocalDateTime.now().plusMinutes(1));
					otpentity.setUsername(phoneno);

					otpRepo.save(otpentity);

					String msz = "Your Otp " + sendotp + " Kousik Manik Application Otp";

					Message message = Message.creator(new PhoneNumber(phoneno), new PhoneNumber(FROM_NUMBER), msz)
							.create();
					messageDto.setMessage(SEND);
					return messageDto;
				} else {
                    
					otpentity = dbotp.get();
					
					otpRepo.delete(otpentity);

					otpentity.setUsername(phoneno);

					otpentity.setOtp(sendotp);

					otpentity.setOtpExpiry(LocalDateTime.now().plusMinutes(1));

					otpRepo.save(otpentity);

					String msz = "Your Otp " + sendotp + " Kousik Manik Application Otp";

					Message message = Message.creator(new PhoneNumber(phoneno), new PhoneNumber(FROM_NUMBER), msz)
							.create();
					messageDto.setMessage(SEND);
					return messageDto;
				}

			} else {
				messageDto.setMessage("Invalid PhoneNo");
				return messageDto;
			}

		} catch (ParseException e) {
			messageDto.setMessage("Failed to send OTP");
			return messageDto;
		}

	}

	@Override
	public SuccesDto validOtp(Integer otpnum) throws OtpException {

		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		final String OTPNOTVALID = "OTP is not valid or has expired";
		final String VALIDOTP = "Enter A ValidOTp";

		SuccesDto succes = new SuccesDto();

		Users user = new Users();

		try {

			Optional<OtpEntity> dbotp = otpService.getdetails(otpnum);
			OtpEntity otp = dbotp.get();

			if (otp != null && otp.getOtpExpiry().isAfter(LocalDateTime.now())) {

				int serverotp = otp.getOtp();
				if (serverotp == otpnum) {
					String auth = jwtService.generateToken(otp.getUsername());
					if (isValidEmail(otp.getUsername())) {

						user.setEmail(otp.getUsername());
						userRepo.save(user);
						UserDto userDto = userToUserDto.convert(user);

						otpRepo.delete(otp);
						succes.setUserDto(userDto);
						succes.setUserId(auth);
						succes.setSucces(true);
						succes.setMssz(SUCCESS);
						return succes;

					} else {

						user.setPhoneNo(otp.getUsername());

						userRepo.save(user);

						UserDto userDto = userToUserDto.convert(user);

						otpRepo.delete(otp);
						succes.setUserDto(userDto);
						succes.setUserId(auth);
						succes.setSucces(true);
						succes.setMssz(SUCCESS);
						return succes;
					}
				} else {
					throw new OtpException(FAIL);
				}

			} else {
				throw new OtpException(OTPNOTVALID);
			}

		} catch (Exception e) {
			e.getMessage();
			throw new OtpException(OTPNOTVALID);
		}
	}

	@Override
	public SignUpDetailsDto SignUpUsingOtp(SignUpOtpDto signUpOtpDto) throws OtpException {
		// TODO Auto-generated method stub

		SignUpDetailsDto signUpDetailsDto = new SignUpDetailsDto();

		Users user = new Users();
		String hi = signUpOtpDto.getEmailOrPhoneno();
		System.out.println(hi);
		try {
			Optional<OtpEntity> dbotp = otpRepo.findByUsername(signUpOtpDto.getEmailOrPhoneno());
			OtpEntity otp = dbotp.get();
			System.out.println(otp.getUsername());
			if (dbotp.isPresent()) {

				//OtpEntity otp = dbotp.get();

				if (otp != null && otp.getOtpExpiry().isAfter(LocalDateTime.now())) {
					
					if (signUpOtpDto.getOtp().equals(otp.getOtp())) {

						String auth = jwtService.generateToken(signUpOtpDto.getEmailOrPhoneno());
						if (isValidEmail(signUpOtpDto.getEmailOrPhoneno())) {
							Optional<Users> users = userRepo.findByEmail(otp.getUsername());
							if (users.isEmpty()){
								user.setFullName(signUpOtpDto.getFullname());
								user.setEmail(signUpOtpDto.getEmailOrPhoneno());
								userRepo.save(user);
							}else {
								signUpDetailsDto.setMessege("user already exist");
								return signUpDetailsDto;
							}

						} else {
							Optional<Users> users = userRepo.findByPhoneNo(otp.getUsername());
							if (users.isEmpty()){
								user.setFullName(signUpOtpDto.getFullname());
								user.setEmail(signUpOtpDto.getEmailOrPhoneno());
								userRepo.save(user);
							}else {
								signUpDetailsDto.setMessege("user already exist");
								return signUpDetailsDto;
							}

						}
						signUpDetailsDto.setUserId(auth);
						signUpDetailsDto.setMessege("Account created successfully");

						otpRepo.delete(otp);
						return signUpDetailsDto;
					} else {
						signUpDetailsDto.setMessege("Please Enter A Correct Otp");
						return signUpDetailsDto;
					}

				} else {
					signUpDetailsDto.setMessege("Otp Expired");
					return signUpDetailsDto;
				}

			} else {
				signUpDetailsDto.setMessege("User Does Not Exist");
				return signUpDetailsDto;
			}
		} catch (Exception e) {
			signUpDetailsDto.setMessege("Somthing Went Wrong");
			return signUpDetailsDto;
		}

	}

	@Override
	public SignInOtpDetailsDto SignInUsingOtp(SignInOtpDto signInOtpDto) throws OtpException {

		SignInOtpDetailsDto signInOtpDetailsDto = new SignInOtpDetailsDto();

		Optional<OtpEntity> dbotp = otpRepo.findByUsername(signInOtpDto.getEmailOrPhoneno());

		if (dbotp.isPresent()) {

			OtpEntity otp = dbotp.get();

			if (otp != null && otp.getOtpExpiry().isAfter(LocalDateTime.now())) {

				if (signInOtpDto.getOtp().equals(otp.getOtp()) ) {
					if (isValidEmail(signInOtpDto.getEmailOrPhoneno())) {

						String auth = jwtService.generateToken(signInOtpDto.getEmailOrPhoneno());

						Optional<Users> dbuser = userRepo.findByEmail(signInOtpDto.getEmailOrPhoneno());

						Users user = dbuser.get();

						UserDetailsDto userDetailsDto = userToUserDetailsDto.convert(user);

						signInOtpDetailsDto.setUserDetailsDto(userDetailsDto);
						signInOtpDetailsDto.setUserId(auth);
						signInOtpDetailsDto.setLoggedIn(true);
						signInOtpDetailsDto.setIsProfileDetailPresent(user.getIsProfileDetailPresent());

						return signInOtpDetailsDto;

					} else {

						String auth = jwtService.generateToken(signInOtpDto.getEmailOrPhoneno());

						Optional<Users> dbuser = userRepo.findByPhoneNo(signInOtpDto.getEmailOrPhoneno());

						Users user = dbuser.get();

						UserDetailsDto userDetailsDto = userToUserDetailsDto.convert(user);

						signInOtpDetailsDto.setUserDetailsDto(userDetailsDto);
						signInOtpDetailsDto.setUserId(auth);
						signInOtpDetailsDto.setLoggedIn(true);
						signInOtpDetailsDto.setIsProfileDetailPresent(user.getIsProfileDetailPresent());

						return signInOtpDetailsDto;

					}

				}else {
					throw new OtpException("otp not matched please enter a correct otp");
				}
			}else {
				throw new OtpException("otp expired");
			}
		}

		throw new OtpException("User Not Present");

	}

}
