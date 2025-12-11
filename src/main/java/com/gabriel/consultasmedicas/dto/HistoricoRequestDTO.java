package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HistoricoRequestDTO {

    @NotNull(message = "O ID da consulta é obrigatório")
    private Long consultaId;

    @NotBlank(message = "As observações são obrigatórias")
    private String observacoes;

    private String receita;
}