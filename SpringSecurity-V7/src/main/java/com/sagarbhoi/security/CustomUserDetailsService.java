package com.sagarbhoi.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sagarbhoi.entity.Role;
import com.sagarbhoi.entity.User;
import com.sagarbhoi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {  // constructor injection
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		// Find user by username or email
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
		.orElseThrow( () -> new UsernameNotFoundException("User not found with username or email : "+ usernameOrEmail));
		
		Set<Role> roles = user.getRoles();
		
		// User object of spring security requires one parameters for GrantedAuthorities for that we are collecting roles and setting as GA.
		Set<GrantedAuthority> grantedAuthorities = roles.stream()
				.map( (role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		// here we are returning User object of spring core security.
		return new org.springframework.security.core.userdetails.User(usernameOrEmail, user.getPassword(), grantedAuthorities);
	}
}
