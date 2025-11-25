package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.UsuarioRequestDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.Usuario; // O único lugar que importa o Model
import com.gabriel.consultasmedicas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método principal para criação/registro de usuário
    @Override
    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO requestDTO) {
        // 1. Validação de email duplicado
        if (usuarioRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        // 2. Mapeamento DTO para Entidade (USANDO O MODEL)
        Usuario novoUsuario = new Usuario(); 
        novoUsuario.setNome(requestDTO.getNome());
        novoUsuario.setEmail(requestDTO.getEmail());
        novoUsuario.setTipo(requestDTO.getTipo());
        
        // 3. CRIPTOGRAFIA DA SENHA
        String senhaCriptografada = passwordEncoder.encode(requestDTO.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        
        // 4. Salvar no Banco de Dados
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        
        // 5. Mapeamento Entidade para Response DTO
        return toResponseDTO(usuarioSalvo);
    }
    
    // Método para buscar por email (usado pelo Spring Security)
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        // A camada de serviço usa o Model internamente
        return usuarioRepository.findByEmail(email); 
    }

    // Método para buscar e listar usuários por tipo (retorna DTO)
    @Override
    public List<UsuarioResponseDTO> buscarPorTipo(String tipo) {
        // Note que o findAll e o filtro são feitos internamente no Service
        List<Usuario> usuarios = usuarioRepository.findAll(); 
        
        return usuarios.stream()
            .filter(u -> u.getTipo().name().equalsIgnoreCase(tipo))
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    // Método auxiliar (Mapper) para conversão de Entidade para DTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTipo(usuario.getTipo());
        return dto;
    }
    
    // Método para remover (DELETE /usuarios/{id})
    @Override
    @Transactional
    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }
}