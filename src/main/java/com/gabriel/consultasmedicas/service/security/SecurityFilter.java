package com.gabriel.consultasmedicas.service.security;

import com.gabriel.consultasmedicas.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter; 

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter { 

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        var token = this.recoverToken(request);
        
        if (token != null) {
            try {
            	
                UUID userId = jwtService.extractUserId(token);
                
                var usuario = usuarioRepository.findById(userId).orElse(null);

                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                } else {
                    System.out.println("Filtro: Usuário não encontrado para o ID -> " + userId);
                }
            } catch (Exception e) {
                System.err.println("Filtro: Erro na validação do token -> " + e.getMessage());
            }
        } else {
            // Log para quando a requisição chega sem token nenhum
            // System.out.println("Filtro: Requisição sem token para URI -> " + request.getRequestURI());
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}