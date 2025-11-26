package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de Resposta para a entidade Usuario.
 * Usado para retornar dados básicos do usuário (excluindo a senha)
 * após o cadastro ou ao listar usuários.
 */
@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;

    // Não incluir o campo 'senha' por questões de segurança.
}
