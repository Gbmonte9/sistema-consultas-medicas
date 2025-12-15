package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaHistoricoIntegradoDTO(
    @NotNull(message = "O ID do paciente é obrigatório.")
    UUID pacienteId,
    
    @NotNull(message = "O ID do médico é obrigatório.")
    UUID medicoId,
    
    @FutureOrPresent(message = "A data e hora da consulta deve ser futura ou o presente.")
    LocalDateTime dataHora,
    
    String observacoes, 
    String receita 
) {}