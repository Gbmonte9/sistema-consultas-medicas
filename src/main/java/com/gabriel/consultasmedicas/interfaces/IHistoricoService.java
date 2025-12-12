package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;

import java.util.UUID; 

public interface IHistoricoService {
    
    HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto);
    
    HistoricoResponseDTO buscarPorId(UUID id);

    HistoricoResponseDTO buscarPorConsultaId(UUID consultaId); 

    HistoricoResponseDTO atualizar(UUID id, HistoricoRequestDTO dto);
    
    void remover(UUID id);

    byte[] gerarHistoricoConsultasPDF();
    
}