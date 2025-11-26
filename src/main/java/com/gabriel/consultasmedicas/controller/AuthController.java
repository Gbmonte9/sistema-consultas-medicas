package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pela autenticação de usuários (login) e geração do Token JWT.
 * Esta é a única rota pública, junto com /api/usuarios/registrar.
 */
@RestController
@RequestMapping("/api/auth") // Rota padrão para autenticação
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint de Login.
     * Recebe o DTO de login (email e senha) e retorna um objeto contendo o Token JWT.
     * Esta rota deve ser PÚBLICA no SecurityConfig.
     * @param loginDTO Email e senha do usuário.
     * @return 200 OK com o Token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO loginDTO) {
        // O Service cuida da autenticação (usando AuthenticationManager) e da geração do token JWT.
        String token = authService.autenticarEGerarToken(loginDTO);

        // Retorna o token em um formato simples (pode ser um DTO mais complexo se necessário)
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Classe interna para formatar a resposta do login, contendo o token.
     */
    private record AuthResponse(String token) {}
}