package com.sagarbhoi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException,
																																	WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(usernameNotFoundException.getMessage());
		errorDetails.setPath(webRequest.getDescription(false)); 			//here if we pass true then it will give all client info but if you want only path then pass false
		errorDetails.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailAlreadyExistsException,
																																	WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(emailAlreadyExistsException.getMessage());
		errorDetails.setPath(webRequest.getDescription(false)); 			//here if we pass true then it will give all client info but if you want only path then pass false
		errorDetails.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}
}
