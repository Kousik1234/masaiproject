package com.masaiproject.Enum;

public enum CredibilityScore {
	
	LOW("low"),MEDIUM("medium"),HIGH("high");
	
	String value;
	
	CredibilityScore(String value){
		this.value = value;
	}
}