package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO; // DTO de entrada para agendar
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;

/**
 * Interface que define o contrato de negócio para a entidade Consulta (Agendamento).
 * Responsável por toda a lógica de agendamento, conflito de horário e cancelamento.
 */
public interface IConsultaService {
    
    // 1. Agendamento da Consulta (Consolida os métodos duplicados)
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);
    
    // 2. Cancelamento da Consulta
    void cancelar(Long id);
    
    // 3. Busca de consultas por ID
    ConsultaResponseDTO buscarPorId(Long id);

    // 4. Listar consultas de um médico
    List<ConsultaResponseDTO> listarPorMedico(Long medicoId);
    
    // 5. Listar consultas de um paciente
    List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId);
    
    // 6. Listar consultas de um médico por Status (ex: AGENDADA, CANCELADA, REALIZADA)
    List<ConsultaResponseDTO> listarPorMedicoEStatus(Long medicoId, StatusConsulta status);
    
    // 7. Listar consultas de um paciente por Status
    List<ConsultaResponseDTO> listarPorPacienteEStatus(Long pacienteId, StatusConsulta status);

    // 8. Listar todas (Admin)
    List<ConsultaResponseDTO> listarTodas();

	List<ConsultaResponseDTO> listarPorMedicoId(Long medicoId);

	List<ConsultaResponseDTO> listarPorPacienteId(Long pacienteId);

	ConsultaResponseDTO atualizarStatus(Long id, StatusConsulta novoStatus);
}