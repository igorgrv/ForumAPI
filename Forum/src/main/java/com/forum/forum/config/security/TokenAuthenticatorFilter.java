package com.forum.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.forum.forum.model.User;
import com.forum.forum.repository.UserRepository;

public class TokenAuthenticatorFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UserRepository userRepository;
	
	public TokenAuthenticatorFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		

		String token = getToken(request);
		boolean tokenIsValid = tokenService.isValid(token);
		if (tokenIsValid) {
			authUser(token);
		}		
		filterChain.doFilter(request, response);
	}

	private void authUser(String token) {
		Long idUser = tokenService.getUserId(token);
		User user = userRepository.findById(idUser).get();
		UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
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
