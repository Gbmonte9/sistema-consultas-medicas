package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HistoricoRequestDTO {

    // ID da consulta que está sendo finalizada
    @NotNull(message = "O ID da consulta é obrigatório")
    private Long consultaId;

    // Observações/Diagnóstico (Obrigatório)
    @NotBlank(message = "As observações são obrigatórias")
    private String observacoes;

    // Receita (Pode ser opcional)
    private String receita;
}