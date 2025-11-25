package com.gabriel.consultasmedicas.dto.auth;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data 
public class LoginRequest {
    @NotBlank(message = "O email é obrigatório.")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}