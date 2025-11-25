// IUsuarioService.java
package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.Optional;

import com.gabriel.consultasmedicas.dto.UsuarioRequestDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.model.Usuario;

public interface IUsuarioService {
    
    // NOVO: Registro usando DTO
    UsuarioResponseDTO criar(UsuarioRequestDTO request);
    
    // NOVO: Busca por email (para segurança)
    Optional<Usuario> buscarPorEmail(String email);
    
    // NOVO: Busca por tipo (para listagens)
    List<UsuarioResponseDTO> buscarPorTipo(String tipo);
    
    // NOVO: Remover
    void remover(Long id);
    
    // Remova: Usuario cadastrarUsuario(Usuario usuario);
    // Remova: List<Usuario> listarTodos();
    // Remova: Usuario buscarPorId(Long id);
    // Remova: Usuario salvar(Usuario usuario);
    // Remova: Usuario registrar(Usuario usuario);
    // Remova: Usuario autenticar(String email, String senha);
}