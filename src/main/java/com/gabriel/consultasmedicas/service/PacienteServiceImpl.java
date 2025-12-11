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

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository pacienteRepository;
    private final IUsuarioService usuarioService; 

    public PacienteServiceImpl(PacienteRepository pacienteRepository, IUsuarioService usuarioService) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public PacienteResponseDTO criar(PacienteCadastroDTO dto) {
  
        if (pacienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado.");
        }
        
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.PACIENTE);
        
        usuarioService.criar(usuarioDto);
        
        Usuario usuario = usuarioService.buscarPorEmail(dto.getEmail())
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuário base não encontrado após criação."));
        
        Paciente novoPaciente = new Paciente();
        novoPaciente.setCpf(dto.getCpf());
        novoPaciente.setTelefone(dto.getTelefone());
        novoPaciente.setUsuario(usuario); 
        
        Paciente pacienteSalvo = pacienteRepository.save(novoPaciente);

        return toResponseDTO(pacienteSalvo);
    }

    @Override
    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteCadastroDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        
        Optional<Paciente> cpfExistente = pacienteRepository.findByCpf(dto.getCpf());
        if (cpfExistente.isPresent() && !cpfExistente.get().getId().equals(id)) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado por outro paciente.");
        }

        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        
        Usuario usuario = paciente.getUsuario();
        
        usuarioService.atualizar(usuario.getId(), 
                                 new UsuarioCadastroDTO(dto.getNome(), dto.getEmail(), dto.getSenha(), TipoUsuario.PACIENTE));
        
        return toResponseDTO(pacienteRepository.save(paciente));
    }

    @Override
    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));
        return toResponseDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPorCpf(String cpf) {
        Paciente paciente = pacienteRepository.findByCpf(cpf)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado pelo CPF."));
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
    public void remover(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para remoção."));
        
        Long usuarioId = paciente.getUsuario().getId();
        
        pacienteRepository.delete(paciente);
        usuarioService.remover(usuarioId);
    }

    private PacienteResponseDTO toResponseDTO(Paciente paciente) {
        return PacienteResponseDTO.builder()
            .id(paciente.getId())
            .cpf(paciente.getCpf())
            .telefone(paciente.getTelefone())
            .nomeUsuario(paciente.getUsuario().getNome())
            .emailUsuario(paciente.getUsuario().getEmail())
            .tipo(paciente.getUsuario().getTipo())
            .build();
    }
}