package com.clinique.app.ws.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.clinique.app.ws.responses.errors.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(value = {UserException.class})
	public ResponseEntity<Object> HandleUserException(UserException ex , WebRequest request){
		
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),new Date());
		
		return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> HandleOthersException(Exception ex , WebRequest request){
		
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),new Date());
		
		return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
