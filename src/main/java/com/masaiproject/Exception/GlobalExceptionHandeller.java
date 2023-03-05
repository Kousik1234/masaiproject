package com.masaiproject.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class GlobalExceptionHandeller {


	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErorDetails> userExceptionHandler(UserException ce, WebRequest req){
		
		
		MyErorDetails err= new MyErorDetails();
			err.setTimeStamp(LocalDateTime.now());
			err.setMessage(ce.getMessage());
			err.setHttpStatus(HttpStatus.NOT_FOUND);
			err.setDetails(req.getDescription(false));
				
		return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CourseException.class)
	public ResponseEntity<MyErorDetails> courseExceptionHandler(CourseException ce, WebRequest req){
		
		
		MyErorDetails err= new MyErorDetails();
			err.setTimeStamp(LocalDateTime.now());
			err.setMessage(ce.getMessage());
			err.setHttpStatus(HttpStatus.NOT_FOUND);
			err.setDetails(req.getDescription(false));
				
		return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ScoreException.class)
	public ResponseEntity<MyErorDetails> ScoreExceptionHandler(CourseException ce, WebRequest req){
		
		
		MyErorDetails err= new MyErorDetails();
			err.setTimeStamp(LocalDateTime.now());
			err.setMessage(ce.getMessage());
			err.setHttpStatus(HttpStatus.NOT_FOUND);
			err.setDetails(req.getDescription(false));
				
		return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(JwtException.class)
    public ResponseEntity<MyErorDetails> handleJwtException(JwtException ex,WebRequest req) {
		MyErorDetails err= new MyErorDetails();
		err.setTimeStamp(LocalDateTime.now());
		err.setMessage(ex.getMessage());
		err.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		err.setDetails(req.getDescription(false));
			
	  return new ResponseEntity<MyErorDetails>(err, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(OtpException.class)
    public ResponseEntity<MyErorDetails> handleOtpException(OtpException ex,WebRequest req) {
		MyErorDetails err= new MyErorDetails();
		err.setTimeStamp(LocalDateTime.now());
		err.setMessage(ex.getMessage());
		err.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		err.setDetails(req.getDescription(false));
			
	  return new ResponseEntity<MyErorDetails>(err, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErorDetails> otherExceptionHandler(Exception se, WebRequest req){
		
		
		MyErorDetails err= new MyErorDetails();
			err.setTimeStamp(LocalDateTime.now());
			err.setMessage(se.getMessage());
			err.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			err.setDetails(req.getDescription(false));
				
		return new ResponseEntity<MyErorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
}

