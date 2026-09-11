package com.sagarbhoi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
		http.authorizeHttpRequests((requests) -> requests
					.requestMatchers(HttpMethod.GET, "/api/user", "/api/admin").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/welcome").permitAll() 		// any user can able to access /welcome api without authentications.
				);	
		http.formLogin(withDefaults());
		http.httpBasic(basic -> basic.disable());
		return http.build();
	}
}
