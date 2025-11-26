package com.gabriel.consultasmedicas.dto.auth;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de Resposta de Autenticação.
 * Retorna o token de acesso e informações básicas do usuário.
 */
@Data
@Builder // Usamos o Builder para facilitar a criação do objeto de resposta
public class AuthResponseDTO {

    private String token; // Token JWT gerado pelo servidor (usado em requisições futuras)
    private Long id; // ID do Usuário
    private String nome;
    private String email;
    private TipoUsuario tipo; // O tipo é importante para o frontend saber qual tela mostrar
    private String mensagem; // Mensagem de sucesso, se necessário
}