package com.impossibl.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

	private final SecretKey key;
	private final long expirationMs;

	public JwtUtil(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration}") long expirationMs
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}

	// create a signed token for a username
	public String generateToken(String username) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
				.subject(username)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(key)
				.compact();
	}

	// extract the username from a token (also validates signature + expiry)
	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	// validate: returns true if signature is valid and not expired
	public boolean isValid(String token) {
		try {
			parseClaims(token);  // throws if invalid/expired
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}