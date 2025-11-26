package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.TipoUsuario;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de Resposta para a entidade Medico.
 * Usado para retornar dados do médico (ID, nome de usuário, CRM, especialidade) 
 * de forma segura, sem expor dados sensíveis do Usuario.
 */
@Data
@Builder
public class MedicoResponseDTO {

    private Long id; // ID do Medico
    private String nomeUsuario; // Nome do Usuario associado
    private String emailUsuario; // Email do Usuario associado
    private TipoUsuario tipo; // Sempre MEDICO
    private String crm;
    private String especialidade;
}