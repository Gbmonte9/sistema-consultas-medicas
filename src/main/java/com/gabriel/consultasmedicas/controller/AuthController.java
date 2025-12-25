package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.auth.LoginRequestDTO; // Import atualizado
import com.gabriel.consultasmedicas.dto.auth.LoginResponseDTO; // Import atualizado
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        
        LoginResponseDTO authResponse = authService.autenticarEGerarToken(loginDTO);
        
        return ResponseEntity.ok(authResponse);
    }
}