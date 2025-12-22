package com.gabriel.consultasmedicas.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID; 

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    public String generateToken(UUID userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        
        claims.put("userId", userId.toString()); 
        claims.put("role", role);

        return buildToken(claims, userId.toString()); 
    }
    
    private String buildToken(Map<String, Object> extraClaims, String subject) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, String claimName, Class<T> claimType) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName, claimType);
    }
    
    public UUID extractUserId(String token) {
        String userIdString = extractClaim(token, "userId", String.class);
        if (userIdString != null) {
            return UUID.fromString(userIdString);
        }
        return null;
    }
    
    // MUDANÇA AQUI: Removido Decoders.BASE64.decode para aceitar qualquer string do application.properties
    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}