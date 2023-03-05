package com.masaiproject.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Setter
@Getter
@Table(name = "otp")
public class OtpEntity {
    
	@Id
	private Integer otp;
	
    private LocalDateTime otpExpiry;
    
    private String username;
   
	
}
