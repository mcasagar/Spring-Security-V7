package com.sagarbhoi.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		 	
		response.setHeader("error-reason", "Authorization Failed");
		response.setContentType("application/json");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setPath(request.getRequestURI());
		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
		errorResponse.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
		errorResponse.setMessage(accessDeniedException.getMessage() + " - You don't have access to this resource");
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

	}

}
