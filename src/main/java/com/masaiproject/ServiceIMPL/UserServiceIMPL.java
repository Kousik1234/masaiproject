package com.masaiproject.ServiceIMPL;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.masaiproject.Entity.Users;
import com.masaiproject.Exception.JwtException;
import com.masaiproject.Exception.UserException;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.JwtService;
import com.masaiproject.Service.UserService;
import com.masaiproject.custommapper.UserDtoToUser;
import com.masaiproject.custommapper.UserToUserDto;
import com.masaiproject.dto.SignInDetailsDto;
import com.masaiproject.dto.SignInDto;
import com.masaiproject.dto.SignUpDto;
import com.masaiproject.dto.TokenResultDto;
import com.masaiproject.dto.UpdateUserDetailsDto;
import com.masaiproject.dto.UserDto;
import com.masaiproject.dto.UserDto.Notification;
import com.masaiproject.dto.UserServiceDto;



@Service
public class UserServiceIMPL implements UserService{
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserToUserDto userToUserDto;
	
	@Autowired
	private UserDtoToUser userDtoToUser;
	
	
	
	private boolean isValidEmail(String input) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailPattern);
    }
	
	private boolean isValidPhone(String input) {
        String phonePattern = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        return input.matches(phonePattern);
    }
	
	
	
	public Users dtoTousers(UserDto userDto) {
		return this.modelMapper.map(userDto, Users.class);
	}
	
	public UserDto usersToDTO(Users users) {
		return this.modelMapper.map(users, UserDto.class);
	}
	
	public Users dtoTousers1(UserServiceDto userDto) {
		return this.modelMapper.map(userDto, Users.class);
	}
	
	public UserServiceDto usersServiceToDTO(UserDto users) {
		return this.modelMapper.map(users, UserServiceDto.class);
	}
	


	
	@Override
	public TokenResultDto signUpUser(SignUpDto signUpDto) throws UserException {
		// TODO Auto-generated method stub
		
		
		BCryptPasswordEncoder bcrEncoder = new BCryptPasswordEncoder();
		
		//Users users = dtoToAuthusers(authuserDTO);
		
		final String msz = "succesfull";
		
		Users users = new Users();
		
		TokenResultDto token = new TokenResultDto();
	
		if(isValidEmail(signUpDto.getEmailOrPhoneno())) {
		    Optional<Users> opUser = userRepo.findByEmail(signUpDto.getEmailOrPhoneno());
			
			if(opUser.isEmpty()) {
			String encryptpass = bcrEncoder.encode(signUpDto.getPassword());
			
			users.setEmail(signUpDto.getEmailOrPhoneno());
		
	        users.setPassword(encryptpass);
	        users.setFullName(signUpDto.getFullname());
	        
	        users = userRepo.save(users);
	        
	        String auth = jwtService.generateToken(signUpDto.getEmailOrPhoneno());
			
			token.setUserId(auth);
			token.setMsz(msz);
			}else {
				throw new UserException("user already exist");
			}
		}
		
		
		if(isValidPhone(signUpDto.getEmailOrPhoneno())) {
		
			Optional<Users> opUser1 = userRepo.findByPhoneNo(signUpDto.getEmailOrPhoneno());
			
			if(opUser1.isEmpty()) {
			String encryptpass = bcrEncoder.encode(signUpDto.getPassword());
			 
			users.setPhoneNo(signUpDto.getEmailOrPhoneno());
			
	        users.setPassword(encryptpass);
	        users.setFullName(signUpDto.getFullname());
	        
	        users = userRepo.save(users);
	        
	        String auth = jwtService.generateToken(signUpDto.getEmailOrPhoneno());
			
			token.setUserId(auth);
			token.setMsz(msz);
			}else {
				throw new UserException("user already exist");
			}
		}
		
		
		return token;
	}
	
	

	@Override
	public SignInDetailsDto signInUser(SignInDto signInDto) throws UserException {
		// TODO Auto-generated method stub
		
		BCryptPasswordEncoder bcrEncoder = new BCryptPasswordEncoder();
		
		Optional<Users> opUser = userRepo.findByEmail(signInDto.getEmailOrPhoneno());
		
		if(opUser.isPresent()) {
			
			Users dbuser = opUser.get();
			
			if(bcrEncoder.matches(signInDto.getPassword(), dbuser.getPassword())) {
				SignInDetailsDto signInDetailsDto = new SignInDetailsDto();
				
				String auth = jwtService.generateToken(signInDto.getEmailOrPhoneno());
				
				UserDto userDto = userToUserDto.convert(dbuser);
				
				signInDetailsDto.setUserDto(userDto);
				signInDetailsDto.setUserId(auth);
				signInDetailsDto.setMessege("signin succesfull");
				return signInDetailsDto;
			}
			else {
				throw new UserException("incorrect password");
			}
		}else {
			throw new UserException("please enter a correct gmail");
		}
		
	}

	

	@Override
	public UserServiceDto getdetailsuser(String userId) throws UserException ,JwtException{
		
		Boolean check = jwtService.validateAuthToken(userId);

		UserServiceDto userServiceDto = new UserServiceDto();
		UserDto userDto = new UserDto();
		
		try {
			
			if(check) {
				
				String username =jwtService.extractUsername(userId);
				if(isValidEmail(username)) {
					Optional<Users> user = userRepo.findByEmail(username);
					Users dbuser = user.get();
					
					userDto = userToUserDto.convert(dbuser);
					
					userServiceDto.setUserDto(userDto);
					userServiceDto.setIsProfileDetailPresent(dbuser.getIsProfileDetailPresent());
					userServiceDto.setLoggedIn(true);
					
				}if(isValidPhone(username)) {
					Optional<Users> user = userRepo.findByEmail(username);
					Users dbuser = user.get();
					
					userDto = userToUserDto.convert(dbuser);
					
					userServiceDto.setUserDto(userDto);
					userServiceDto.setIsProfileDetailPresent(dbuser.getIsProfileDetailPresent());
					userServiceDto.setLoggedIn(true);
				}
				
			}else {
				
				throw new UserException("user not found");
			}
			
			
			
		}catch(JwtException e) {
			throw new JwtException("invalid token");
		}
		
		return userServiceDto;
		
	}

	@Override
	public String updateuser(String userId,UserDto userDto) throws UserException, JwtException {
		 
		Boolean check = jwtService.validateAuthToken(userId);
		
		System.out.print(check);
		
		Notification notification = new Notification();
		
		
	    
		
		try {
			
			if(check) {
				
				String username = jwtService.extractUsername(userId);
				if(isValidEmail(username)) {
					Optional<Users> user = userRepo.findByEmail(username);
					if(user.isPresent()) {
						
						 Users dbuser = user.get();
		
						 
						 dbuser.setIsProfileDetailPresent(true);
					
	
						 
						 dbuser.setGender(userDto.getGender());
						 dbuser.setDob(userDto.getDob());
						 dbuser.setGraduatedOn(userDto.getGraduatedOn());
						 dbuser.setCompletedTwelthOn(userDto.getCompletedTwelthOn());
						 dbuser.setCurrentWorkingStatus(userDto.getCurrentWorkingStatus());
						 dbuser.setPhoneNo(userDto.getPhoneNo());
						 dbuser.setJobReady(userDto.getJobReady());
						 dbuser.setReferralCode(userDto.getReferralCode());
						 
						 notification=userDto.getNotification();
						 
						 dbuser.setCallsNotification(notification.getCalls());
						 dbuser.setEmailNotification(notification.getEmail());
						 dbuser.setNewsletterNotification(notification.getNewsletter());
						 dbuser.setWhatsAppNotification(notification.getWhatsapp());
						 
						 
						 
						 userRepo.save(dbuser);
						 
						return "Updated profile details successfully";
					}else {
						throw new UserException("user not found");
					}	
					
				}if(isValidPhone(username)) {
					Optional<Users> user = userRepo.findByPhoneNo(username);
					if(user.isPresent()) {
						
						 Users dbuser = user.get();
						
						 
						 dbuser.setIsProfileDetailPresent(true);
					
						 dbuser.setGender(userDto.getGender());
						 dbuser.setDob(userDto.getDob());
						 dbuser.setGraduatedOn(userDto.getGraduatedOn());
						 dbuser.setCompletedTwelthOn(userDto.getCompletedTwelthOn());
						 dbuser.setCurrentWorkingStatus(userDto.getCurrentWorkingStatus());
						 dbuser.setEmail(userDto.getEmail());
						 dbuser.setJobReady(userDto.getJobReady());
						 dbuser.setReferralCode(userDto.getReferralCode());
						 
                         notification=userDto.getNotification();
						 
						 dbuser.setCallsNotification(notification.getCalls());
						 dbuser.setEmailNotification(notification.getEmail());
						 dbuser.setNewsletterNotification(notification.getNewsletter());
						 dbuser.setWhatsAppNotification(notification.getWhatsapp());
						 
						 userRepo.save(dbuser);
						 
						return "Updated profile details successfully";
					}else {
						throw new UserException("user not found");
					}	
				}
				
			}else {
				throw new JwtException("token not valis");
			}
			
			
			
		}catch(UserException e) {
			e.getMessage();
		}
		
	 throw new JwtException("token invalid");
	
	
	}

}

