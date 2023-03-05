package com.masaiproject.Entity;

public enum Status {
	
	PASSED("passed"), FAILED("failed");
	
	String value;
	
	Status(String value){
		this.value = value;
	}
	

}
