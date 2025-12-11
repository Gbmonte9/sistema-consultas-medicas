package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.model.StatusConsulta;

/**
 * Contrato de operações relacionadas à lógica de consultas médicas:
 * agendamento, cancelamento, atualização de status e listagem.
 */
public interface IConsultaService {
    
    // -----------------------------
    // CRUD / Ações da Consulta
    // -----------------------------

    /** Agendar uma nova consulta */
    ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto);

    /** Cancelar uma consulta existente */
    void cancelar(Long id);

    /** Remover consulta definitivamente do sistema (ADMIN) */
    void remover(Long id);

    /** Finalizar uma consulta (status REALIZADA) */
    void finalizar(Long id);

    /** Atualizar o status manualmente (ADMIN/MÉDICO) */
    ConsultaResponseDTO atualizarStatus(Long id, StatusConsulta novoStatus);

    // -----------------------------
    // Consultas básicas
    // -----------------------------
    
    /** Buscar consulta por ID */
    ConsultaResponseDTO buscarPorId(Long id);

    /** Listar todas as consultas (ADMIN) */
    List<ConsultaResponseDTO> listarTodas();

    // -----------------------------
    // Listagens por Médico
    // -----------------------------

    /** Listar todas as consultas de um médico */
    List<ConsultaResponseDTO> listarPorMedico(Long medicoId);

    /** Listar consultas de um médico filtrando por Status */
    List<ConsultaResponseDTO> listarPorMedicoEStatus(Long medicoId, StatusConsulta status);

    // -----------------------------
    // Listagens por Paciente
    // -----------------------------

    /** Listar todas as consultas de um paciente */
    List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId);

    /** Listar consultas de um paciente filtrando por Status */
    List<ConsultaResponseDTO> listarPorPacienteEStatus(Long pacienteId, StatusConsulta status);

	List<ConsultaResponseDTO> listarPorMedicoId(Long medicoId);

	List<ConsultaResponseDTO> listarPorPacienteId(Long pacienteId);
}
