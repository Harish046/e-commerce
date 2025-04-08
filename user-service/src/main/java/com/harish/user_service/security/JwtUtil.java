package com.harish.user_service.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private long expiry;
	
	private SecretKey getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
	private SecretKey getKey() {
	    String keyBytes = Encoders.BASE64.encode(jwtSecret.getBytes());
	    return Keys.hmacShaKeyFor(keyBytes.getBytes());
	}
	
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiry))
				.signWith(getKey())
				.compact();
	}
	
	public String extractUserName(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
				Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
