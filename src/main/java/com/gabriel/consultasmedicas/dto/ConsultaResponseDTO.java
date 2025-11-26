package com.gabriel.consultasmedicas.dto;

import java.time.LocalDateTime;

import com.gabriel.consultasmedicas.model.StatusConsulta;

import lombok.Data;

@Data
public class ConsultaResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    
    // Usamos DTOs aninhados para expor apenas o necessário do Médico e Paciente
    private MedicoConsultaDTO medico;
    private PacienteConsultaDTO paciente;
    
    // -----------------------------------------------------------------------------------
    // DTOs Aninhados: Mostram apenas nome/especialidade/crm ou nome/cpf do paciente
    // -----------------------------------------------------------------------------------

    @Data
    public static class MedicoConsultaDTO {
        private Long id;
        private String nome; // Nome do usuário associado ao médico
        private String especialidade;
        private String crm;
    }

    @Data
    public static class PacienteConsultaDTO {
        private Long id;
        private String nome; // Nome do usuário associado ao paciente
        private String cpf;
    }
}