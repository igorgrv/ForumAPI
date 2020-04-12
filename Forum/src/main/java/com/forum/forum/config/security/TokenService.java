package com.forum.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.forum.forum.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String experation;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String tokenGenerator(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		/**
		 * .setIssuer - inform, the API responsible for the token generation;
		 * .setSubject - inform the user resposible;
		 * .setIssuedAt - inform the Current date;
		 * .setExpiration - inform the time to expire the token - in our case, it will be 1 day;
		 * .signWith - it's the type of the algorith that will be generated, and our secret password;
		 * .compact - transform to String; 
		 */
		return Jwts.builder()
				.setIssuer("Igor - ForumAPI")
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + Long.parseLong(experation)))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
