package com.masaiproject.Entity;

public enum CredibilityScore {
	
	LOW("low"),MEDIUM("medium"),HIGH("high");
	
	String value;
	
	CredibilityScore(String value){
		this.value = value;
	}
}
