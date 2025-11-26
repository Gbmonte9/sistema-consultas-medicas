package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;

/**
 * Interface que define o contrato de negócio para a entidade Paciente.
 * Utiliza DTOs para comunicação com a camada Controller.
 */
public interface IPacienteService {
    
    // Método para registrar/criar um novo paciente (usa DTO de Entrada)
    PacienteResponseDTO criar(PacienteCadastroDTO dto);
    
    // Método para buscar um paciente pelo ID
    PacienteResponseDTO buscarPorId(Long id);
    
    // Método para buscar um paciente pelo CPF (chave de negócio)
    PacienteResponseDTO buscarPorCpf(String cpf);
    
    // Método para atualizar informações de um paciente
    PacienteResponseDTO atualizar(Long id, PacienteCadastroDTO dto);
    
    // Método para listar todos os pacientes (usado em painel administrativo)
    List<PacienteResponseDTO> listarTodos();
    
    // Método para remover um paciente
    void remover(Long id);
}