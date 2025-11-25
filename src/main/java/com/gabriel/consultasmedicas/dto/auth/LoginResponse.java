package com.gabriel.consultasmedicas.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token; // Ou uma mensagem de sucesso, se não usar JWT.
}