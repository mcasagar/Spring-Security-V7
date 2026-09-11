package com.sagarbhoi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
		http.authorizeHttpRequests((requests) -> requests
					.requestMatchers(HttpMethod.GET, "/api/user", "/api/admin").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/welcome").permitAll() 		// any user can able to access /welcome api without authentications.
				);	
		http.formLogin(form -> form.disable());	// disabled form base authentications
		http.httpBasic(withDefaults());
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user = User.builder()
				.username("lalit")
				.password(passwordEncorder().encode("lalit123"))
				.roles("USER")	
				.build();
		
		UserDetails admin = User.builder()
				.username("sagar")
				.password(passwordEncorder().encode("sagar123"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
}
