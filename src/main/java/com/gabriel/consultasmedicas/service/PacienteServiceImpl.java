package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.interfaces.IPacienteService;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.Paciente;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.PacienteRepository;
import com.gabriel.consultasmedicas.service.util.CpfEncryptor; 

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID; 
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository pacienteRepository;
    private final IUsuarioService usuarioService;    
    private final CpfEncryptor cpfEncryptor; 

    public PacienteServiceImpl(
        PacienteRepository pacienteRepository, 
        IUsuarioService usuarioService,
        CpfEncryptor cpfEncryptor 
    ) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioService = usuarioService;
        this.cpfEncryptor = cpfEncryptor; 
    }

    @Override
    @Transactional
    public PacienteResponseDTO criar(PacienteCadastroDTO dto) {
        String encryptedCpf = cpfEncryptor.encrypt(dto.getCpf());
        
        if (pacienteRepository.findByCpf(encryptedCpf).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado.");
        }
        
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.PACIENTE);
        
        usuarioService.criar(usuarioDto);
        
        Usuario usuario = usuarioService.buscarPorEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuário base não encontrado."));
        
        Paciente novoPaciente = new Paciente();
        novoPaciente.setCpf(encryptedCpf); 
        novoPaciente.setTelefone(dto.getTelefone());
        novoPaciente.setUsuario(usuario);    
        
        return toResponseDTO(pacienteRepository.save(novoPaciente));
    }

    @Override
    @Transactional
    public PacienteResponseDTO atualizar(UUID id, PacienteCadastroDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        
        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            String newEncryptedCpf = cpfEncryptor.encrypt(dto.getCpf());
            
            if (!newEncryptedCpf.equals(paciente.getCpf())) {
                if (pacienteRepository.findByCpf(newEncryptedCpf).isPresent()) {
                     throw new ResponseStatusException(HttpStatus.CONFLICT, "Novo CPF já cadastrado.");
                }
                paciente.setCpf(newEncryptedCpf);
            }
        }

        paciente.setTelefone(dto.getTelefone());
        
        usuarioService.atualizar(paciente.getUsuario().getId(), 
            new UsuarioCadastroDTO(
                dto.getNome(), 
                dto.getEmail(), 
                dto.getSenha(), 
                TipoUsuario.PACIENTE
            )
        );
        
        return toResponseDTO(pacienteRepository.save(paciente));
    }

    @Override
    public PacienteResponseDTO buscarPorId(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        return toResponseDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPorCpf(String cpf) {
        String encryptedCpf = cpfEncryptor.encrypt(cpf);
        Paciente paciente = pacienteRepository.findByCpf(encryptedCpf)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        return toResponseDTO(paciente);
    }

    @Override
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remover(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        
        UUID usuarioId = paciente.getUsuario().getId();
        pacienteRepository.delete(paciente);
        usuarioService.remover(usuarioId);
    }

    private PacienteResponseDTO toResponseDTO(Paciente paciente) {
        String cpfDescriptografado = cpfEncryptor.decrypt(paciente.getCpf());

        return PacienteResponseDTO.builder()
            .id(paciente.getId())
            .cpf(cpfDescriptografado) 
            .telefone(paciente.getTelefone())
            .nomeUsuario(paciente.getUsuario().getNome())
            .emailUsuario(paciente.getUsuario().getEmail())
            .tipo(paciente.getUsuario().getTipo())
            .build();
    }
}