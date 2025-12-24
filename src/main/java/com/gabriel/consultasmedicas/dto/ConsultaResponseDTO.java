package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;
import com.gabriel.consultasmedicas.model.StatusConsulta;
import lombok.Builder;
import lombok.Data;
import java.util.UUID; 

@Data
@Builder
public class ConsultaResponseDTO {

    private UUID id;
    private LocalDateTime dataHora;
    private LocalDateTime dataFim;    
    private StatusConsulta status;
    private String motivo; 
    private MedicoConsultaDTO medico;
    private PacienteConsultaDTO paciente;

    @Data
    @Builder
    public static class MedicoConsultaDTO {
        private UUID id;    
        private String nome;
        private String especialidade;
        private String crm;
    }

    @Data
    @Builder
    public static class PacienteConsultaDTO {
        private UUID id;    
        private String nome;
        private String cpf;
        private String email; 
    }
}