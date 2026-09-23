package com.sagarbhoi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthResponseDto {
	
	private String accessToken;
	private String tokenType = "Bearer";
	
	public JwtAuthResponseDto(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
