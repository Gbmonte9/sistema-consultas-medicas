package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.auth.AuthRequestDTO;
import com.gabriel.consultasmedicas.dto.auth.AuthResponseDTO; 
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.UsuarioRepository;
import com.gabriel.consultasmedicas.service.security.JwtService; 
import com.gabriel.consultasmedicas.model.TipoUsuario; 

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.UUID; 

@Service
public class AuthServiceImpl implements IAuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 

    public AuthServiceImpl(
        UsuarioRepository usuarioRepository, 
        PasswordEncoder passwordEncoder,
        JwtService jwtService 
    ) {
    	this.usuarioRepository = usuarioRepository;
    	this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService; 
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
	public AuthResponseDTO autenticarEGerarToken(AuthRequestDTO requestDTO) {
        
        Usuario usuarioAutenticado = autenticar(requestDTO);

        UUID userId = usuarioAutenticado.getId();
        
        String role = usuarioAutenticado.getTipo().toString(); 
        
        String token = jwtService.generateToken(userId, role);

        return AuthResponseDTO.builder()
            .token(token)
            .id(userId)
            .nome(usuarioAutenticado.getNome())
            .email(usuarioAutenticado.getEmail())
            .tipo(usuarioAutenticado.getTipo()) 
            .mensagem("Login realizado com sucesso.")
            .build();
	}
}