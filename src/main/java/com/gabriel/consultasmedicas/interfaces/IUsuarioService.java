package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.Optional;

import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;

/**
 * Interface que define o contrato de regras de negócio para a entidade Usuario.
 * Mantém o desacoplamento da camada Controller, permitindo fácil manutenção e troca de implementações.
 */
public interface IUsuarioService {

    // -----------------------------------------------------------------------------------
    // CRUD (Create, Read, Update, Delete)
    // -----------------------------------------------------------------------------------

    /**
     * Registra um novo usuário no sistema.
     */
    UsuarioResponseDTO criar(UsuarioCadastroDTO requestDTO);

    /**
     * Busca um usuário pelo ID.
     */
    UsuarioResponseDTO buscarPorId(Long id);

    /**
     * Lista todos os usuários cadastrados.
     */
    List<UsuarioResponseDTO> listarTodos();

    /**
     * Remove um usuário pelo ID.
     */
    void remover(Long id);

    // -----------------------------------------------------------------------------------
    // CONSULTAS ESPECÍFICAS
    // -----------------------------------------------------------------------------------

    /**
     * Busca um usuário pelo e-mail (usado principalmente pelo Spring Security).
     */
    Optional<Usuario> buscarPorEmail(String email);

    /**
     * Lista usuários filtrando por tipo (ex.: ADMIN, PACIENTE, MEDICO).
     */
    List<UsuarioResponseDTO> buscarPorTipo(TipoUsuario tipo);

    // -----------------------------------------------------------------------------------
    // Futuro: adicionar método de atualização caso necessário.
    // -----------------------------------------------------------------------------------
}
