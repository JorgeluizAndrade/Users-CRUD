package com.project.rest.usersRestApi.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.project.rest.usersRestApi.modal.User;

@Service
public class JWTCreator {
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String ROLES_AUTHORITIES = "authorities";

	@Autowired
	private SecurityConfig securityConfig;

	public String generateToken(User user) {
		try {
			  Algorithm algorithm = Algorithm.HMAC256(securityConfig.getKey());
	            String token = JWT.create()
	                    .withIssuer("auth-api")
	                    .withSubject(user.getUsername())
	                    .withExpiresAt(genExpirationDate())
	                    .sign(algorithm);
	            return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(securityConfig.getKey());

			return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();

		} catch (JWTCreationException exception) {
			return "";
		}
	}
	

	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
