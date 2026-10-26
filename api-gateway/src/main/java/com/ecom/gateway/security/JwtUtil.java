package com.ecom.gateway.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	@Value("${app.jwt.secret}")
	private String secret;
	
	private SecretKey key;
	
	@PostConstruct
	private void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes()); //create key only once dont't create of every request
	}
	public Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public boolean validateToken(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public String getUserId(String token) {
		return extractClaims(token).getSubject();
	}
	public String getRoles(String token) {
		return extractClaims(token).get("role",String.class);
	}
}
