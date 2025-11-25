package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.model.Usuario;

public interface IUsuarioService {
    Usuario registrar(Usuario usuario);
    Usuario autenticar(String email, String senha);
    List<Usuario> listarTodos();
    void remover(Long id);
	Usuario cadastrarUsuario(Usuario usuario);
	Usuario buscarPorId(Long id);
	Usuario salvar(Usuario usuario);
}
