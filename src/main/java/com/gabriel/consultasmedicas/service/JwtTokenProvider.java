package com.gabriel.consultasmedicas.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Componente responsável por criar, assinar e validar os Tokens JWT.
 * Utiliza a chave secreta e o tempo de expiração definidos no application.properties.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret; // Chave secreta injetada das propriedades

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationInMs; // Tempo de validade injetado das propriedades

    // A chave de assinatura é gerada uma vez usando a string secreta
    private Key key;

    // Inicializa a chave de assinatura na construção do componente
    private Key getSigningKey() {
        if (this.key == null) {
            // Decodifica a string base64url-safe da chave secreta.
            // Para garantir que a chave tenha o tamanho mínimo de 256 bits, usamos o Keys.hmacShaKeyFor
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }
        return this.key;
    }

    /**
     * Gera um token JWT para um usuário autenticado.
     * @param userDetails Detalhes do usuário (implementa UserDetails).
     * @return O token JWT como String.
     */
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Constrói o token JWT
        return Jwts.builder()
                .setSubject(username) // Define o 'subject' (identificador do usuário)
                .setIssuedAt(new Date()) // Define a data de emissão
                .setExpiration(expiryDate) // Define a data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assina com a chave secreta e algoritmo HS256
                .compact();
    }

    /**
     * Extrai o nome de usuário (subject) do token JWT.
     * @param token O token JWT.
     * @return O nome de usuário (email).
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Valida a integridade e a validade de um token JWT.
     * @param authToken O token JWT a ser validado.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException ex) {
            log.error("Assinatura JWT inválida");
        } catch (MalformedJwtException ex) {
            log.error("Token JWT inválido");
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT não suportado");
        } catch (IllegalArgumentException ex) {
            log.error("String de claims JWT vazia");
        }
        return false;
    }
}