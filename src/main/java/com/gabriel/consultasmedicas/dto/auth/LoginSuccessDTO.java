package com.gabriel.consultasmedicas.dto.auth;

public record LoginSuccessDTO(
    String token, 
    String role, 
    Long userId
) {}