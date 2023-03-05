package com.masaiproject.Service;

import com.masaiproject.Entity.Response;
import com.masaiproject.Exception.ScoreException;
import com.masaiproject.dto.ScoreDto;

public interface ScoreService {

	public String addScore(String userId ,ScoreDto scoreDto) throws ScoreException;

	
	public Response DisplayResult();
}