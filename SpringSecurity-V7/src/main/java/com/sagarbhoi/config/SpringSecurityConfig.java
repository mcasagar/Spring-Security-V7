package com.sagarbhoi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	public SpringSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain customSecurityFilterChain(HttpSecurity http) {
		http.authorizeHttpRequests((requests) -> requests
//					.requestMatchers(HttpMethod.GET, "/api/user", "/api/admin").authenticated()
//					.requestMatchers(HttpMethod.GET, "/api/welcome").permitAll() 		// any user can able to access /welcome api without authentications.
					
					// Role Base Authorizations
//					.requestMatchers(HttpMethod.GET, "/api/user").hasAnyRole("ADMIN", "USER")  // here both admin & user can access /user api
//					.requestMatchers(HttpMethod.GET, "/api/admin").hasRole("ADMIN")					   // here only admin can access /admin api
					.requestMatchers(HttpMethod.GET, "/api/welcome").permitAll()							  // here /welcome api can access anyone.
					.anyRequest().authenticated()																				     // other than above api, every request will check credentials
				);	
		
		http.formLogin(form -> form.disable());	// disabled form base authentications
		http.httpBasic(withDefaults());
		return http.build();
	}
	
	// Now we are loading users from database and performing authentications so there no need for inMemoryUserDetailsManager etc..
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user = User.builder()
//				.username("lalit")
//				.password(passwordEncorder().encode("lalit123"))
//				.roles("USER")	
//				.build();
//		
//		UserDetails admin = User.builder()
//				.username("sagar")
//				.password(passwordEncorder().encode("sagar123"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//	}
}
