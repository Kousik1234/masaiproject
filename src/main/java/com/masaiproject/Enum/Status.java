package com.masaiproject.Enum;

public enum Status {
	
	PASSED("passed"), FAILED("failed");
	
	String value;
	
	Status(String value){
		this.value = value;
	}
	

}
