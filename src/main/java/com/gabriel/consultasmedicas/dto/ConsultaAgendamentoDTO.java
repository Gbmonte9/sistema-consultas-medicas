package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultaAgendamentoDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data e hora do agendamento devem ser futuras")
    private LocalDateTime dataHora;

	public ConsultaAgendamentoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsultaAgendamentoDTO(@NotNull(message = "O ID do paciente é obrigatório") Long pacienteId,
			@NotNull(message = "O ID do médico é obrigatório") Long medicoId,
			@NotNull(message = "A data e hora são obrigatórias") @Future(message = "A data e hora do agendamento devem ser futuras") LocalDateTime dataHora) {
		super();
		this.pacienteId = pacienteId;
		this.medicoId = medicoId;
		this.dataHora = dataHora;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public Long getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	
    
    
}