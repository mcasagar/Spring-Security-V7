package com.sagarbhoi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sagarbhoi.dto.RegisterDto;
import com.sagarbhoi.entity.Role;
import com.sagarbhoi.entity.User;
import com.sagarbhoi.repository.RoleRepository;
import com.sagarbhoi.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncorder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	//constructor injection
	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncorder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncorder = passwordEncorder;
	}
	
	@Override
	public String register(RegisterDto registerDto) {
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncorder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);
		
		User savedUser = userRepository.save(user);
		
		return "User Registered successfully...";
		
	}

}
