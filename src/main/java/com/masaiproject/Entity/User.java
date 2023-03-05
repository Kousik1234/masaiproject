package com.masaiproject.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

	

	public User(Integer userId) {
		// TODO Auto-generated constructor stub
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
    private String Name;
	
	private String Email;
	
	private String MobileNo;
	
	private LocalDate DateOfBirth;
	
	//private Gender gender;
	
	private Job job;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Education education;


	 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Score> scores;
	
	
	
	
}
