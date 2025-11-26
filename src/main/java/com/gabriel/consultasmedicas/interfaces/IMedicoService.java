package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;

/**
 * Interface que define o contrato de negócio para a entidade Medico.
 * Utiliza DTOs para comunicação com a camada Controller.
 */
public interface IMedicoService {
    
    // Método para registrar/criar um novo médico (usa DTO de Entrada)
    MedicoResponseDTO criar(MedicoCadastroDTO dto);
    
    // Método para buscar um médico pelo ID
    MedicoResponseDTO buscarPorId(Long id);
    
    // Método para buscar um médico pelo CRM (chave de negócio)
    MedicoResponseDTO buscarPorCrm(String crm);
    
    // Método crucial para a tela de agendamento (busca por filtro)
    List<MedicoResponseDTO> buscarPorEspecialidade(String especialidade);
    
    // Método para atualizar informações de um médico
    MedicoResponseDTO atualizar(Long id, MedicoCadastroDTO dto);
    
    // Método para listar todos os médicos (usado em painel administrativo)
    List<MedicoResponseDTO> listarTodos();
    
    // Método para remover um médico
    void remover(Long id);
}