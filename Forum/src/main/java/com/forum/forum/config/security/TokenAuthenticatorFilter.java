package com.forum.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticatorFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	
	public TokenAuthenticatorFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		
		boolean tokenIsValid = tokenService.isValid(token);
		System.out.println(tokenIsValid);
		
		filterChain.doFilter(request, response);
	}

	//this method will validate if the token was sent
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token.isEmpty() || token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
