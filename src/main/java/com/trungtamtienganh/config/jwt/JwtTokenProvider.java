package com.trungtamtienganh.config.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${TOKEN_EXPIRED}")
	private long TOKEN_EXPIRED;

	@Value("${TOKEN_SECRET}")
	private String TOKEN_SECRET;
	
	@Value("${DATABASE_USERNAME}")
	private String DATABASE_USERNAME;
	
	@Value("${DATABASE_USERNAME}")
	private static String aaaa;

	public String generateToken(Integer userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRED);
		return Jwts.builder().setSubject(String.valueOf(userId)).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

}
