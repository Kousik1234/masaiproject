package com.masaiproject.ServiceIMPL;



import java.util.List;
import java.util.Optional;

import com.masaiproject.Entity.Course;
import com.masaiproject.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masaiproject.Entity.Response;
import com.masaiproject.Entity.Score;
import com.masaiproject.Entity.Users;
import com.masaiproject.Exception.ScoreException;
import com.masaiproject.Repositry.ScoreRepo;
import com.masaiproject.Repositry.UserRepo;
import com.masaiproject.Service.ScoreService;
import com.masaiproject.dto.ScoreDto;



@Service
public class ScoreServiceImpl implements ScoreService{

	@Autowired
    private	ScoreRepo scoreRapo;
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private org.modelmapper.ModelMapper model;
	
	public Score dtoToScore(ScoreDto scoreDto) {
		return this.model.map(scoreDto, Score.class);
	}

	public ScoreDto ScoreToDTO(Score score) {
		return this.model.map(score, ScoreDto.class);
	}


	private boolean isValidEmail(String input) {
		String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return input.matches(emailPattern);
	}

	private boolean isValidPhone(String input) {
		String phonePattern = "^\\+(?:[0-9] ?){6,14}[0-9]$";
		return input.matches(phonePattern);
	}
	
	@Override
	public String addScore(String userId ,ScoreDto scoreDto) throws ScoreException {
		
         if(jwtService.validateAuthToken(userId)){

			 String username = jwtService.extractUsername(userId);
			 if(isValidEmail(username)){
				 Optional<Users> users = userRepo.findByEmail(username);
				 if(users.isPresent()){
					 Users user = users.get();
					 Score score = new Score();
					 Course course = new Course();
					 score.setCredibilityScore(scoreDto.getCredibilityScore());
					 score.setMettleTestScore(scoreDto.getMettleTestScore());
					 score.setCognitiveAbility(scoreDto.getCognitiveAbility());
					 score.setCommunicationSkills(scoreDto.getCommunicationSkills());
                     score.setUsers(user);
					 score.setCourse(course);

					 scoreRapo.save(score);
                     return "succesfull";
				 }
			 }else{
                 Optional<Users> users = userRepo.findByPhoneNo(username);
				 if (users.isPresent()){
					 Users user = users.get();
					 Score score = new Score();

					 score.setCredibilityScore(scoreDto.getCredibilityScore());
					 score.setMettleTestScore(scoreDto.getMettleTestScore());
					 score.setCognitiveAbility(scoreDto.getCognitiveAbility());
					 score.setCommunicationSkills(scoreDto.getCommunicationSkills());
					 score.setUsers(user);

					 scoreRapo.save(score);
					 return "succesfull";
				 }
			 }
		 }else{
			 throw new ScoreException("Invalid Test Score");
		 }

		return null;

		
	}


	@Override
	public Response DisplayResult() {
		
		List<Score> findAll = scoreRapo.findAllById(null);
		
		Score score = findAll.get(findAll.size() - 1);
		
		Response response = new Response();
		
		response.setStatus(null);
		response.setCourseResponceDto(null);
		response.setScoteDto(null);
		return response;
	}
	
}
