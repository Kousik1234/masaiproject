package com.masaiproject.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.masaiproject.Enum.AfterCompleteMasai;
import com.masaiproject.Enum.Gender;
import com.masaiproject.Enum.Graduate;
import com.masaiproject.Enum.TwelvePass;
import com.masaiproject.Enum.WorkingStatus;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Setter
@Getter
@Table(name = "user")
public class Users {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;
	
	private String email;
	
	private String phoneNo;
	
	private LocalDate dob;
	
	private String password;
	
	private String referralCode;
	
	private Gender gender;
	
	private String graduatedOn;
	
	private String completedTwelthOn;
	
	private AfterCompleteMasai jobReady;
	
	private WorkingStatus currentWorkingStatus;
	
	private Boolean isProfileDetailPresent = false;
	
	
	private Boolean whatsAppNotification = false;
	
	private Boolean emailNotification = false;
	
	private Boolean callsNotification = false;
	private Boolean newsletterNotification = false;


	@ManyToMany(mappedBy = "users")
	private List<Course> courses = new ArrayList<>();
	
	
	
	public static boolean isPhoneNumber(String input) {
	    Pattern pattern = Pattern.compile("^\\+?[0-9. ()-]{10,25}$"); // Regex for phone number
	    Matcher matcher = pattern.matcher(input);
	    return matcher.matches();
	}

	public static boolean isEmail(String input) {
	    Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"); // Regex for email
	    Matcher matcher = pattern.matcher(input);
	    return matcher.matches();
	}

}

