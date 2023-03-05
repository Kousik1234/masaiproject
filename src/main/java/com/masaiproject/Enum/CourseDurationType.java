package com.masaiproject.Enum;

public enum CourseDurationType {

	WEEKS("week"),DAYS("days"),MINUTES("minutes"),HOURS("hours"),MONTHS("month"),
	YEAR("year");
	
	String value;
	
	CourseDurationType(String value){
		this.value = value;
	}
}
