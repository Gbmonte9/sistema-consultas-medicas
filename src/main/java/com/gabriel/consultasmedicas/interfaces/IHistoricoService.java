package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;


public interface IHistoricoService {
    
    HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto);
    
    HistoricoResponseDTO buscarPorId(Long id);

    HistoricoResponseDTO buscarPorConsultaId(Long consultaId); 

    HistoricoResponseDTO atualizar(Long id, HistoricoRequestDTO dto);
    
    void remover(Long id);

    byte[] gerarHistoricoConsultasPDF();
    
}