package com.masaiproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MySecurityConfig {

	@Bean
    public SecurityFilterChain newfilterChain(HttpSecurity http) throws Exception {
        
	   http
	   .csrf()
          .disable();
        
        return http.build();
    }
	
	
	
}
