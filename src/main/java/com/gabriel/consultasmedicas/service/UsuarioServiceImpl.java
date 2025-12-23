package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID; 
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO criar(UsuarioCadastroDTO requestDTO) {
        if (usuarioRepository.findByEmail(requestDTO.getEmail().trim().toLowerCase()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(requestDTO.getNome());
        novoUsuario.setEmail(requestDTO.getEmail().trim().toLowerCase());
        novoUsuario.setTipo(requestDTO.getTipo());

        String senhaCriptografada = passwordEncoder.encode(requestDTO.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return toResponseDTO(usuarioSalvo);
    }
    
    @Override
    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        return toResponseDTO(usuario);
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email.trim().toLowerCase());
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorTipo(TipoUsuario tipo) {
        List<Usuario> usuarios = usuarioRepository.findByTipo(tipo);
        return usuarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizar(UUID id, UsuarioCadastroDTO requestDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().isBlank()) {
            String novoEmail = requestDTO.getEmail().trim().toLowerCase();
            if (!novoEmail.equalsIgnoreCase(usuarioExistente.getEmail())) {
                if (usuarioRepository.findByEmail(novoEmail).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado por outro usuário.");
                }
                usuarioExistente.setEmail(novoEmail);
            }
        }
        
        if (requestDTO.getNome() != null && !requestDTO.getNome().isBlank()) {
            usuarioExistente.setNome(requestDTO.getNome());
        }
        
        if (requestDTO.getTipo() != null) {
            usuarioExistente.setTipo(requestDTO.getTipo());
        }
        
        if (requestDTO.getSenha() != null && !requestDTO.getSenha().isBlank()) {
            String novaSenhaCriptografada = passwordEncoder.encode(requestDTO.getSenha());
            usuarioExistente.setSenha(novaSenhaCriptografada);
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return toResponseDTO(usuarioAtualizado);
    }

    @Override
    @Transactional
    public void remover(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId()) 
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .tipo(usuario.getTipo())
                .build();
    }
}