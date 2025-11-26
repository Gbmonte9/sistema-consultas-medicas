package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
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
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements IMedicoService {

    private final MedicoRepository medicoRepository;
    private final IUsuarioService usuarioService; // Para criar a conta de login

    public MedicoServiceImpl(MedicoRepository medicoRepository, IUsuarioService usuarioService) {
        this.medicoRepository = medicoRepository;
        this.usuarioService = usuarioService;
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE CRIAÇÃO E ATUALIZAÇÃO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public MedicoResponseDTO criar(MedicoCadastroDTO dto) {
        // 1. Regra de Negócio: CRM deve ser único
        if (medicoRepository.findByCrm(dto.getCrm()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CRM já cadastrado.");
        }
        
        // 2. Criação do Usuário base (Login/Senha/Nome/Email)
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.MEDICO);
        
        // Chama o UsuarioService para criptografar a senha e salvar o registro Usuario
        UsuarioResponseDTO usuarioCriado = usuarioService.criar(usuarioDto);
        
        // 3. Busca a entidade Usuario salva para associar ao Medico
        Usuario usuario = usuarioService.buscarPorEmail(usuarioCriado.getEmail())
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário base não encontrado após criação."));
        
        // 4. Mapeamento DTO para Entidade Medico
        Medico novoMedico = new Medico();
        novoMedico.setCrm(dto.getCrm());
        novoMedico.setEspecialidade(dto.getEspecialidade());
        novoMedico.setUsuario(usuario); // Associa o registro Usuario
        
        // 5. Salva o registro Medico
        Medico medicoSalvo = medicoRepository.save(novoMedico);

        // 6. Retorna o DTO de Resposta
        return toResponseDTO(medicoSalvo);
    }

    @Override
    @Transactional
    public MedicoResponseDTO atualizar(Long id, MedicoCadastroDTO dto) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));

        // Atualiza apenas os campos específicos do Medico
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        
        // Atualiza o nome e email no registro Usuario associado
        Usuario usuario = medico.getUsuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        
        return toResponseDTO(medicoRepository.save(medico));
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE BUSCA E LISTAGEM
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

    // Método crucial para a tela de agendamento: Listar por Especialidade
    @Override
    public List<MedicoResponseDTO> buscarPorEspecialidade(String especialidade) {
        // Assume-se que o MedicoRepository possui um método findByEspecialidade
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
    // MÉTODO DE REMOÇÃO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public void remover(Long id) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado para remoção."));
        
        Long usuarioId = medico.getUsuario().getId();
        
        medicoRepository.delete(medico);
        usuarioService.remover(usuarioId); // Remove o registro de login também
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: DTO Mappers
    // -----------------------------------------------------------------------------------

    private MedicoResponseDTO toResponseDTO(Medico medico) {
        // Mapeia para o DTO de Resposta
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