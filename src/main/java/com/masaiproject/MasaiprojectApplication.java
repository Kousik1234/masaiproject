package com.masaiproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@EnableWebMvc
public class MasaiprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasaiprojectApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
        return new ModelMapper();
	}

}
