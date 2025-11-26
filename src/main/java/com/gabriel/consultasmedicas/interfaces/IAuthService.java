package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;

/**
 * Interface que define o contrato para a lógica de autenticação e segurança.
 * Responsável por validar credenciais e gerar o Token JWT.
 */
public interface IAuthService {

    /**
     * Autentica o usuário com base nas credenciais e, se bem-sucedido, gera um token JWT.
     * @param requestDTO DTO contendo email e senha.
     * @return O Token JWT (String).
     */
    String autenticarEGerarToken(AuthRequestDTO requestDTO);
}