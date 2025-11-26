package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.Optional;

import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;

/**
 * Interface que define as operações de lógica de negócio para a entidade Usuario.
 * Isso garante o desacoplamento e facilita a troca de implementações (Service).
 */
public interface IUsuarioService {

    // Método para registrar um novo usuário no sistema
    UsuarioResponseDTO criar(UsuarioCadastroDTO requestDTO);

    // Método usado pelo Spring Security para buscar um usuário pelo email
    Optional<Usuario> buscarPorEmail(String email);

    // Método para buscar usuários por tipo (ex: listar todos os ADMINs)
    List<UsuarioResponseDTO> buscarPorTipo(TipoUsuario tipo);

    // Método para remover um usuário pelo ID
    void remover(Long id);
    
    // Futuro: Adicionar métodos de atualização e busca por ID aqui.
}