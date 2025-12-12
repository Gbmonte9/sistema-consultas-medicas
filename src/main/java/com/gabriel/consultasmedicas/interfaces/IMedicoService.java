package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;


public interface IMedicoService {
    
    MedicoResponseDTO criar(MedicoCadastroDTO dto);
    
    MedicoResponseDTO buscarPorId(Long id);
    
    MedicoResponseDTO buscarPorCrm(String crm);
    
    List<MedicoResponseDTO> buscarPorEspecialidade(String especialidade);
    
    MedicoResponseDTO atualizar(Long id, MedicoCadastroDTO dto);
    
    List<MedicoResponseDTO> listarTodos();
    
    void remover(Long id);
}