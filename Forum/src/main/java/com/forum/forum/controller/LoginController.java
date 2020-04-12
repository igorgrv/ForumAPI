package com.forum.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.forum.config.security.TokenService;
import com.forum.forum.controller.dto.TokenDTO;
import com.forum.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> authenticat(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken login = form.toUser();
		
		//we need to handle the error in case the login does not exist.
		try {
			//When Spring calls this method, this method will call the Service that will call the Repository, 
			// that will validate the User and password;
			Authentication authentication = authManager.authenticate(login);
			
			//If the authentication worked, we must return the token, from the JJWT library
			// but the the token generation will be absctracted from controller, using the tokenService!
			String token = tokenService.tokenGenerator(authentication);
			
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
