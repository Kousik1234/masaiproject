package com.masaiproject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.masaiproject.Entity.Users;
import com.masaiproject.Repositry.UserRepo;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Users> userInfo = repository.findByFullname(username);
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//
//    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	if(isValidEmail(username)) {
        Optional<Users> userInfo = repository.findByEmail(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    	}
//    	if(isValidPhone(username)) {
//    		 Optional<Users> userInfo1 = repository.findByPhoneno(username);
//    		return userInfo1.map(UserInfoUserDetails::new)
//                    .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//    	}
    	Optional<Users> userInfo1 = repository.findByPhoneNo(username);
		return userInfo1.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
    
    
    
    
    private boolean isValidEmail(String input) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailPattern);
    }
	
	private boolean isValidPhone(String input) {
        String phonePattern = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        return input.matches(phonePattern);
    }
}