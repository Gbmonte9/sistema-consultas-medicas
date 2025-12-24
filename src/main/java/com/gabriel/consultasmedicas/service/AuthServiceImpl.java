package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.auth.LoginRequestDTO; 
import com.gabriel.consultasmedicas.dto.auth.LoginResponseDTO; 
import com.gabriel.consultasmedicas.interfaces.IAuthService;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.model.Medico; // Importe o Modelo Medico
import com.gabriel.consultasmedicas.model.Paciente; // Importe o Modelo Paciente
import com.gabriel.consultasmedicas.repository.UsuarioRepository;
import com.gabriel.consultasmedicas.repository.PacienteRepository;
import com.gabriel.consultasmedicas.repository.MedicoRepository; // Descomentado
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
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository; // Descomentado
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 

    // Construtor atualizado com MedicoRepository
    public AuthServiceImpl(
        UsuarioRepository usuarioRepository, 
        PacienteRepository pacienteRepository,
        MedicoRepository medicoRepository, 
        PasswordEncoder passwordEncoder,
        JwtService jwtService 
    ) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService; 
    }
    
    @Override
    public Usuario autenticar(LoginRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        if (!passwordEncoder.matches(requestDTO.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        return usuario;
    }
    
    @Override
    public LoginResponseDTO autenticarEGerarToken(LoginRequestDTO requestDTO) {
        Usuario usuarioAutenticado = autenticar(requestDTO);

        UUID userId = usuarioAutenticado.getId();
        String role = usuarioAutenticado.getTipo().toString(); 
        String token = jwtService.generateToken(userId, role);

        String telefone = null;
        String cpf = null;
        String crm = null;
        String especialidade = null;
        
        // Lógica para PACIENTE
        if (usuarioAutenticado.getTipo() == TipoUsuario.PACIENTE) {
            Paciente paciente = pacienteRepository.findByUsuarioId(userId).orElse(null);
            if (paciente != null) {
                telefone = paciente.getTelefone();
                cpf = paciente.getCpf();
            }
        }
        
        // Lógica para MEDICO corrigida
        else if (usuarioAutenticado.getTipo() == TipoUsuario.MEDICO) {
            Medico medico = medicoRepository.findByUsuarioId(userId).orElse(null);
            if (medico != null) {
                crm = medico.getCrm();
                especialidade = medico.getEspecialidade();
            }
        }

        return new LoginResponseDTO(
            token,
            userId,
            usuarioAutenticado.getNome(),
            usuarioAutenticado.getEmail(),
            role,
            telefone, 
            cpf,
            crm, 
            especialidade 
        );
    }
}