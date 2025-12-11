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
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements IMedicoService {

    private final MedicoRepository medicoRepository;
    private final IUsuarioService usuarioService; 

    public MedicoServiceImpl(MedicoRepository medicoRepository, IUsuarioService usuarioService) {
        this.medicoRepository = medicoRepository;
        this.usuarioService = usuarioService;
    }

    // -----------------------------------------------------------------------------------
    // CREATE
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public MedicoResponseDTO criar(MedicoCadastroDTO dto) {
        // 1. Regra de Negócio: CRM deve ser único
        if (medicoRepository.findByCrm(dto.getCrm()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado.");
        }
        
        // 2. Criação do Usuário (Delegação)
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.MEDICO);
        
        usuarioService.criar(usuarioDto);
        
        // 3. Busca a entidade Usuario para associar
        Usuario usuario = usuarioService.buscarPorEmail(dto.getEmail())
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuário base não encontrado após criação."));
        
        // 4. Mapeamento e Salva o Medico
        Medico novoMedico = new Medico();
        novoMedico.setCrm(dto.getCrm());
        novoMedico.setEspecialidade(dto.getEspecialidade());
        novoMedico.setUsuario(usuario); 
        
        Medico medicoSalvo = medicoRepository.save(novoMedico);

        return toResponseDTO(medicoSalvo);
    }

    // -----------------------------------------------------------------------------------
    // UPDATE
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public MedicoResponseDTO atualizar(Long id, MedicoCadastroDTO dto) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));

        // 1. Regra de Negócio: Checar se o novo CRM está em uso por OUTRO médico
        Optional<Medico> crmExistente = medicoRepository.findByCrm(dto.getCrm());
        if (crmExistente.isPresent() && !crmExistente.get().getId().equals(id)) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado por outro médico.");
        }

        // 2. Atualiza campos específicos do Medico
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        
        // 3. DELEGA a atualização do Usuario para o Serviço responsável
        Usuario usuario = medico.getUsuario();
        
        // O UsuarioService cuida de checar e-mail, nome e criptografia de senha.
        usuarioService.atualizar(usuario.getId(), 
                                 new UsuarioCadastroDTO(dto.getNome(), dto.getEmail(), dto.getSenha(), TipoUsuario.MEDICO));
        
        // 4. Salva o Medico
        return toResponseDTO(medicoRepository.save(medico));
    }


    // -----------------------------------------------------------------------------------
    // READ (Buscas e Listagem)
    // -----------------------------------------------------------------------------------

    @Override
    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
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

    // -----------------------------------------------------------------------------------
    // DELETE
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public void remover(Long id) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado para remoção."));
        
        Long usuarioId = medico.getUsuario().getId();
        
        medicoRepository.delete(medico);
        usuarioService.remover(usuarioId); // Remove o registro de login
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: DTO Mappers
    // -----------------------------------------------------------------------------------

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