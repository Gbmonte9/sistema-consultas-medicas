package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.Medico;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.repository.MedicoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements IMedicoService {

    private final MedicoRepository medicoRepository;
    private final IUsuarioService usuarioService;    

    public MedicoServiceImpl(MedicoRepository medicoRepository, IUsuarioService usuarioService) {
        this.medicoRepository = medicoRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public MedicoResponseDTO criar(MedicoCadastroDTO dto) {
        if (medicoRepository.findByCrm(dto.getCrm()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado.");
        }
        
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.MEDICO);
        
        usuarioService.criar(usuarioDto);
        
        Usuario usuario = usuarioService.buscarPorEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuário base não encontrado após criação."));
        
        Medico novoMedico = new Medico();
        novoMedico.setCrm(dto.getCrm());
        novoMedico.setEspecialidade(dto.getEspecialidade());
        novoMedico.setUsuario(usuario);    
        
        Medico medicoSalvo = medicoRepository.save(novoMedico);
        return toResponseDTO(medicoSalvo);
    }

    @Override
    @Transactional
    public MedicoResponseDTO atualizar(UUID id, MedicoCadastroDTO dto) {
        Medico medico = medicoRepository.findById(id)
            .orElseGet(() -> medicoRepository.findAll().stream()
                .filter(m -> m.getUsuario().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado com ID: " + id)));

        if (dto.getCrm() != null && !dto.getCrm().isBlank()) {
            if (!dto.getCrm().equals(medico.getCrm())) {
                Optional<Medico> crmExistente = medicoRepository.findByCrm(dto.getCrm());
                if (crmExistente.isPresent()) {
                     throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado por outro médico.");
                }
                medico.setCrm(dto.getCrm());
            }
        }

        if (dto.getEspecialidade() != null && !dto.getEspecialidade().isBlank()) {
            medico.setEspecialidade(dto.getEspecialidade());
        }
        
        Usuario usuario = medico.getUsuario();
        usuarioService.atualizar(usuario.getId(), 
                                 new UsuarioCadastroDTO(
                                     dto.getNome(), 
                                     dto.getEmail(), 
                                     dto.getSenha(),
                                     TipoUsuario.MEDICO
                                 ));
        
        Medico medicoSalvo = medicoRepository.save(medico);
        return toResponseDTO(medicoSalvo);
    }

    @Override
    public MedicoResponseDTO buscarPorId(UUID id) {
        Medico medico = medicoRepository.findById(id)
            .orElseGet(() -> medicoRepository.findAll().stream()
                .filter(m -> m.getUsuario().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado.")));
        return toResponseDTO(medico);
    }

    @Override
    public MedicoResponseDTO buscarPorCrm(String crm) {
        Medico medico = medicoRepository.findByCrm(crm)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado pelo CRM."));
        return toResponseDTO(medico);
    }

    @Override
    public List<MedicoResponseDTO> buscarPorEspecialidade(String especialidade) {
        return medicoRepository.findByEspecialidade(especialidade).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remover(UUID id) {
        Medico medico = medicoRepository.findById(id)
            .orElseGet(() -> medicoRepository.findAll().stream()
                .filter(m -> m.getUsuario().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado para remoção.")));
        
        UUID usuarioId = medico.getUsuario().getId();
        medicoRepository.delete(medico);
        usuarioService.remover(usuarioId);    
    }

    private MedicoResponseDTO toResponseDTO(Medico medico) {
        return MedicoResponseDTO.builder()
            .id(medico.getId())
            .crm(medico.getCrm())
            .especialidade(medico.getEspecialidade())
            .nomeUsuario(medico.getUsuario().getNome())
            .emailUsuario(medico.getUsuario().getEmail())
            .tipo(medico.getUsuario().getTipo())
            .build();
    }
}