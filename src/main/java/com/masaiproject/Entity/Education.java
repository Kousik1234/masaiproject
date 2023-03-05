package com.masaiproject.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Education {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	private String tenthper;
	
	private LocalDate twelvepass;
	
	private String twelveper;
	
	private LocalDate graduatedate;
	
	//private PgDegree pgDegree;
	
	private LocalDate pggraduate;
	
	//private UgDegree ugDegree;
	
	private LocalDate uggraduate;
	
	//private CurentlyWorkingStatus workingstatus;
	
	
	
	

}
