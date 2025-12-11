package com.gabriel.consultasmedicas.interfaces;

import java.util.List;
import java.util.Optional;

import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;

public interface IUsuarioService {

    UsuarioResponseDTO criar(UsuarioCadastroDTO requestDTO);

    UsuarioResponseDTO buscarPorId(Long id);

    List<UsuarioResponseDTO> listarTodos();

    void remover(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    List<UsuarioResponseDTO> buscarPorTipo(TipoUsuario tipo);
    
    UsuarioResponseDTO atualizar(Long id, UsuarioCadastroDTO requestDTO);

}
