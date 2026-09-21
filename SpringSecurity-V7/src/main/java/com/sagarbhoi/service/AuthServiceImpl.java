package com.sagarbhoi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sagarbhoi.dto.LoginDto;
import com.sagarbhoi.dto.RegisterDto;
import com.sagarbhoi.entity.Role;
import com.sagarbhoi.entity.User;
import com.sagarbhoi.exception.EmailAlreadyExistsException;
import com.sagarbhoi.repository.RoleRepository;
import com.sagarbhoi.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncorder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AuthenticationManager authenticationManager;
	
	//constructor injection
	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncorder,
											 AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncorder = passwordEncorder;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public String register(RegisterDto registerDto) {
		
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new UsernameNotFoundException("Username already exists.");
		}
		
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists.");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncorder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		for(String role : registerDto.getRoles()) {
			 roles.add(roleRepository.findByName(role));
		}

		user.setRoles(roles);
		
		User savedUser = userRepository.save(user);
		
		return "User Registered successfully...";
		
	}
	
	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), loginDto.getPassword()
		));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "User logged-in successfully";
	}

}
