package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.exception.AuthenticationFailedException;
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import com.gabriel.consultasmedicas.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço de autenticação.
 * Responsável por gerenciar o processo de login (validação de credenciais) e
 * a geração do token JWT para o usuário autenticado.
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Injeção via construtor
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Tenta autenticar o usuário e, se bem-sucedido, gera um token JWT.
     * @param requestDTO DTO contendo email e senha.
     * @return O Token JWT gerado (String).
     * @throws AuthenticationFailedException Se as credenciais forem inválidas.
     */
    @Override
    public String autenticarEGerarToken(AuthRequestDTO requestDTO) {
        log.info("Tentativa de autenticação para o email: {}", requestDTO.getEmail());

        try {
            // 1. Tenta autenticar o usuário no Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getSenha())
            );

            // 2. Se a autenticação for bem-sucedida, gera o token JWT
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(userDetails);

            log.info("Autenticação e geração de token bem-sucedidas para o email: {}", requestDTO.getEmail());
            return token;

        } catch (AuthenticationException e) {
            // 3. Captura qualquer falha de autenticação (usuário não encontrado ou senha inválida)
            log.warn("Falha de autenticação para o email {}: {}", requestDTO.getEmail(), e.getMessage());
            throw new AuthenticationFailedException("Credenciais de acesso inválidas.");
        }
    }
}