package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthServiceImpl implements IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; 

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario autenticar(AuthRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        if (!passwordEncoder.matches(requestDTO.getSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        return usuario;
    }
    
    @Override
    public String autenticarEGerarToken(AuthRequestDTO requestDTO) {
        throw new UnsupportedOperationException("A funcionalidade de geração de token JWT não está implementada nesta versão do serviço de autenticação.");
    }
}