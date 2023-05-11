package com.example.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private final static String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY4Mzg1MTUxMX0.kfY_kfvvwZgYlbt-WUiiwG3wMfb2O-bAfzYE8y9c7dgML0cjirN8cUsAW4zoWKueYi_ittgzz3wYsJTm88kn4w";
	private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 2_592_000L;
	
	public static String createToken (String nombre, String email) {
		long expirationTime = ACCES_TOKEN_VALIDITY_SECONDS*1_000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		Map<String, Object> extra = new HashMap<>();
		extra.put("nombre",nombre);
		return Jwts.builder().
				setSubject(email)
				.setExpiration(expirationDate)
				.addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
				.compact();				
	}
	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.build()
					.parseClaimsJws(token)
					.getBody();
			String email = claims.getSubject();
			return new UsernamePasswordAuthenticationToken(email, null, java.util.Collections.emptyList());
		} catch (JwtException e) {
			return null;
		}

	}
}
