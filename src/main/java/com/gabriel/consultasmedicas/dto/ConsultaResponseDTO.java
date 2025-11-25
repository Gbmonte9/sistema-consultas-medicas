package com.gabriel.consultasmedicas.dto;

import com.gabriel.consultasmedicas.model.Consulta; // Enum de status
import com.gabriel.consultasmedicas.model.Consulta.StatusConsulta;

import ch.qos.logback.core.status.Status;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaResponseDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String nomePaciente; // Para facilitar o frontend
    private String nomeMedico;   // Para facilitar o frontend
    private LocalDateTime dataHora;
    private String motivo;
    private StatusConsulta status; // AGENDADA, CANCELADA, REALIZADA
}