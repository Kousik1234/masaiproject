package com.masaiproject.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.masaiproject.Exception.ScoreException;
import com.masaiproject.Service.ScoreService;
import com.masaiproject.dto.ScoreDto;

 



@RestController
@RequestMapping("/api/v1")
public class ScoreController {
	
	@Autowired
    private ScoreService scoreService;
	
	
	@PutMapping("/msat/submitScore")
	public ResponseEntity<?> addScoreHandler(@RequestParam("userId") String userId, @RequestBody ScoreDto scoreDto) throws ScoreException {

		try {
			String result = scoreService.addScore(userId,scoreDto);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}catch (Exception e){
			e.getMessage();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}

	}
	

}
