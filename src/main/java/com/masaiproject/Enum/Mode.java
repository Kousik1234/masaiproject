package com.masaiproject.Enum;

public enum Mode {

	
	ONLINE("online") , OFFLINE("ofline");
	
	String value;
	
	Mode(String value){
		this.value = value;
	}
}
