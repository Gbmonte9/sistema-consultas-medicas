package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de Resposta para a entidade Paciente.
 * Usado para retornar dados do paciente (ID, nome de usuário, CPF, telefone) 
 * de forma segura.
 */
@Data
@Builder
public class PacienteResponseDTO {

    private Long id; // ID do Paciente
    private String nomeUsuario; // Nome do Usuario associado (para exibição)
    private String emailUsuario; // Email do Usuario associado (para contato)
    private TipoUsuario tipo; // Sempre PACIENTE
    private String cpf;
    private String telefone;
}