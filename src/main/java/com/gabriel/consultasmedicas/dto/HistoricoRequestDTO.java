package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID; 

@Data
public class HistoricoRequestDTO {

    @NotNull(message = "O ID da consulta é obrigatório")
    private UUID consultaId;

    @NotBlank(message = "As observações são obrigatórias")
    private String observacoes;

    private String receita;
}