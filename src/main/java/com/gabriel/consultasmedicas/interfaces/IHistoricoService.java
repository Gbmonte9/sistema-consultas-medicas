package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;

/**
 * Interface que define o contrato de negócio para a entidade Historico (Prontuário).
 * Utiliza DTOs para comunicação com a camada Controller.
 */
public interface IHistoricoService {
    
    // Método para registrar o histórico (usado pelo Médico após a consulta)
    HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto);
    
    // Método para buscar o histórico pelo ID
    HistoricoResponseDTO buscarPorId(Long id);

    // Método para buscar o histórico associado a uma consulta específica
    HistoricoResponseDTO buscarPorConsultaId(Long consultaId); // Assumindo 1:1

    // Método para atualizar o histórico (caso precise de correção)
    HistoricoResponseDTO atualizar(Long id, HistoricoRequestDTO dto);
    
    // Método para remover um histórico
    void remover(Long id);

    // Método de negócio para gerar relatórios em PDF
    byte[] gerarHistoricoConsultasPDF();
    
    // Futuro: Adicionar List<HistoricoResponseDTO> buscarPorPacienteId(Long pacienteId);
}