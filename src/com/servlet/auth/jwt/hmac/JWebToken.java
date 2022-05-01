package com.servlet.auth.jwt.hmac;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWebToken {

	private static final String SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

	public static String getToken() {

		Instant now = Instant.now();
		return Jwts.builder()
		        .claim("name", "Jane Doe")
		        .claim("email", "jane@example.com")
		        .setSubject("jane")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
		        .signWith(getHMACKey())
		        .compact();
	}
	

	public static Jws<Claims> parseToken(String jwtString) {
		
		Jws<Claims> jwt = Jwts.parserBuilder()
			      .setSigningKey(getHMACKey())
			      .build()
			      .parseClaimsJws(jwtString);
		return jwt;
	}
	
	private static Key getHMACKey() {
		return new SecretKeySpec(Base64.getDecoder().decode(SECRET), 
                SignatureAlgorithm.HS256.getJcaName());
	}

}
