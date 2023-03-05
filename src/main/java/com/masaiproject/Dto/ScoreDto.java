package com.masaiproject.dto;


import com.masaiproject.Enum.CredibilityScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {

	private	Integer cognitiveAbility;
	
	private	Integer	mettleTestScore;
	
	private	Integer	communicationSkills;

	private CredibilityScore credibilityScore;

	

}
