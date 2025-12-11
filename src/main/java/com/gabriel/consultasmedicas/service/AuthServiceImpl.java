package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthServiceImpl implements IAuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Autenticação básica sem Spring Security.
     * @param requestDTO DTO com email e senha
     * @return Usuário autenticado ou lança exceção se inválido
     */
    @Override
    public Usuario autenticar(AuthRequestDTO requestDTO) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        // Verifica a senha (aqui assume senha em texto plano; se estiver criptografada, use BCrypt)
        if (!usuario.getSenha().equals(requestDTO.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        // Retorna o usuário autenticado
        return usuario;
    }

	@Override
	public String autenticarEGerarToken(AuthRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
