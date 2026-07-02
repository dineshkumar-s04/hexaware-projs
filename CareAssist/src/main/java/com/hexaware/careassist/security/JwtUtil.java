package com.hexaware.careassist.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	// Generate JWT Token
	public String generateToken(String username) {

		return Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSigningKey()).compact();
	}

	// Extract Username
	public String extractUsername(String token) {

		return extractClaims(token).getSubject();
	}

	// Validate Token
	public boolean validateToken(String token, String username) {

		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

	// Check Expiry
	private boolean isTokenExpired(String token) {

		return extractClaims(token).getExpiration().before(new Date());
	}

	// Extract Claims
	private Claims extractClaims(String token) {

		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
}
