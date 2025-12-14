package com.gabriel.consultasmedicas.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
    @NotNull String pacienteId, 
    @NotNull String medicoId,   
    @NotNull @Future LocalDateTime dataHora
) {}