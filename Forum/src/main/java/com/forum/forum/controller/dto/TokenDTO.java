package com.forum.forum.controller.dto;

public class TokenDTO {

	private String token;
	private String typeAuthentication;

	public TokenDTO(String token, String typeAuthentication) {
		this.token = token;
		this.typeAuthentication = typeAuthentication;
	}

	public String getToken() {
		return token;
	}

	public String getTypeAuthentication() {
		return typeAuthentication;
	}
	
}
