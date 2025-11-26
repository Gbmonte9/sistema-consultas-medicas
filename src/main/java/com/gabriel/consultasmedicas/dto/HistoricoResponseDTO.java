package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de Resposta para a entidade Historico.
 * Usado para retornar o prontuário de uma consulta específica.
 */
@Data
@Builder
public class HistoricoResponseDTO {

    private Long id;
    private Long consultaId; // ID da consulta associada
    private String observacoes;
    private String receita;
    private LocalDateTime dataRegistro; // Quando o histórico foi preenchido
    
    // Futuramente, pode incluir DTOs aninhados do Medico e Paciente
}