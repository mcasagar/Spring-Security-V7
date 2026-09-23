package com.sagarbhoi.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtTokenProvider jwtTokenProvider;
	private UserDetailsService userDetailsService;
	
	//constructor injection
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
			// get the token from the request 
			String token = getTokenFromRequest(request);
		   // validate token
			if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				  // get username from token
				  String username = jwtTokenProvider.getUsernameFromToken(token);
				  // load user object from the database using username
				  UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				 // then we need to provide user details to spring security for authentication
				  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						  userDetails,
						  null,
						  userDetails.getAuthorities()
				);
				  // so spring security internally uses this request for multiple purpose for that we are setting the details here
				  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 // set authentication object to the security context 
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		 
		// finally we need to handle other Http Request who don't have JWT token in the header for ex
			// login rest api, register api so in order to handle this request we have to call doFilter method
			// of this FilterChain object and pass request and response
		 filterChain.doFilter(request, response);
	}
	
	// method for get token from request
	private String getTokenFromRequest(HttpServletRequest request) {
		//getting token from header using Authorization key 
		// this contains [ bearer jwt_token ]
		String bearerToken = request.getHeader("Authorization");
		 //use util class to remove bearer word
		//fist checks text must have. Second check this string starts with "bearer " name
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")) {
			//remove bearer word
			return bearerToken.substring(7, bearerToken.length()); 
		}
		
		return null;
	}
}

// finally we have to provide this JwtAuthenticationFilter to spring security so that spring security can 
//call JwtAuthenticationFilter before calling any other filters so this we have to do in spring security config class
