package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.UUID;
import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;

public interface IHistoricoService {
    
    HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto);

    HistoricoResponseDTO atualizar(UUID id, HistoricoRequestDTO dto);

    HistoricoResponseDTO buscarPorId(UUID id);

    HistoricoResponseDTO buscarPorConsultaId(UUID consultaId);

    List<HistoricoResponseDTO> buscarPorPacienteId(UUID pacienteId);

    void remover(UUID id);

    byte[] gerarHistoricoConsultasPDF();
}