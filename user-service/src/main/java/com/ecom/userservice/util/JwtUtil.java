package com.ecom.userservice.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final SecretKey key;
	private final long jwtExpirationMs;
	
	public JwtUtil(@Value("${app.jwt.secret}") String key, @Value("${app.jwt.expirationMs}") long jwtExpirationMs) {
		this.key = Keys.hmacShaKeyFor(key.getBytes());
		this.jwtExpirationMs = jwtExpirationMs;
	}
	public String generateToken(long usseId, String email, String role) {
		long now = System.currentTimeMillis();
		String token = Jwts 
		.builder()
		.claim("email", email)
		.claim("role", role)
		.issuedAt(new Date(now))
		.expiration(new Date(jwtExpirationMs))
		.signWith(key)
		.compact();
		
		return token;
	}
	
	public Jws<Claims> validateToken(String token){
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
	}
	
	public Long getUserIdFromToken(String token) {
		Claims claims = validateToken(token).getBody();
		return Long.valueOf(claims.getSubject());
	}
}
