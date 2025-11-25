package com.gabriel.consultasmedicas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }

	@Override
	public Usuario registrar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}
}
