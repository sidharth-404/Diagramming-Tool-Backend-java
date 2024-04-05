package com.diagrammingtool.app.util;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	 public static final long JWT_TOKEN_VALIDITY =1000 * 60 * 60 * 24;


	    private String secret = "asdfghjklqwertyuiopxcvbnmsrtyuiocvbnmdfghsghgdsjhgdshjdnvdhjvdavnbadsvnadvbnadvdhgavdavdafdahgvdanbvdahgvdabndavh";


	//retrieve username from jwt token

	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	//retrieve expiration date from jwt token

	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    // for retrieveing any information from token we will need the secret key

	    private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }
	    
	   

	//check if the token has expired

	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	//generate token for user

	    public String generateToken(String username) {
	       
	        return doGenerateToken(username);
	    }


	    private String doGenerateToken( String subject) {
	    	Claims claims = Jwts.claims().setSubject(subject);

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	    }

	    //validate token
	    public Boolean validateToken(String token, String username2) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(username2) && !isTokenExpired(token));
	    }
}
