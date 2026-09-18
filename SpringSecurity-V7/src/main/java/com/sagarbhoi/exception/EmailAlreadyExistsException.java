package com.sagarbhoi.exception;

public class EmailAlreadyExistsException extends RuntimeException{  // created custom exception
	
	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}
