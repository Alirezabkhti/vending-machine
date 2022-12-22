package com.vub.assessment.vendingmachine.infrastructure;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.vub.assessment.vendingmachine.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    private final static int expirationDeadlineHours = 120;

    private final SecretKey key;
    private final Date tokenIssueDate;
    private final Date tokenExpirationDate;

    public JwtUtil(@Value("${vub.jwt.secret-key}") String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        tokenIssueDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        tokenExpirationDate = Date.from(LocalDateTime.now()
                                                     .plusHours(expirationDeadlineHours)
                                                     .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return setTokenProperties(claims, user.getUsername());
    }

    private String setTokenProperties(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(tokenIssueDate)
                   .setExpiration(tokenExpirationDate)
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}

