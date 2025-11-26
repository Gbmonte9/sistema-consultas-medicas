package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
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
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository pacienteRepository;
    private final IUsuarioService usuarioService; // Serviço de Usuário para criar a conta de login

    public PacienteServiceImpl(PacienteRepository pacienteRepository, IUsuarioService usuarioService) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioService = usuarioService;
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE CRIAÇÃO E ATUALIZAÇÃO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public PacienteResponseDTO criar(PacienteCadastroDTO dto) {
        // 1. Regra de Negócio: CPF deve ser único
        if (pacienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado.");
        }
        
        // 2. Criação do Usuário base (Login/Senha/Nome/Email)
        UsuarioCadastroDTO usuarioDto = new UsuarioCadastroDTO();
        usuarioDto.setNome(dto.getNome());
        usuarioDto.setEmail(dto.getEmail());
        usuarioDto.setSenha(dto.getSenha());
        usuarioDto.setTipo(TipoUsuario.PACIENTE);
        
        // Chama o UsuarioService para criptografar a senha e salvar o registro Usuario
        UsuarioResponseDTO usuarioCriado = usuarioService.criar(usuarioDto);
        
        // 3. Busca a entidade Usuario salva para associar ao Paciente
        Usuario usuario = usuarioService.buscarPorEmail(usuarioCriado.getEmail())
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário base não encontrado após criação."));
        
        // 4. Mapeamento DTO para Entidade Paciente
        Paciente novoPaciente = new Paciente();
        novoPaciente.setCpf(dto.getCpf());
        novoPaciente.setTelefone(dto.getTelefone());
        novoPaciente.setUsuario(usuario); // Associa o registro Usuario
        
        // 5. Salva o registro Paciente
        Paciente pacienteSalvo = pacienteRepository.save(novoPaciente);

        // 6. Retorna o DTO de Resposta
        return toResponseDTO(pacienteSalvo);
    }

    @Override
    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteCadastroDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));

        // Atualiza apenas os campos específicos do Paciente
        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        
        // Atualiza o nome e email no registro Usuario associado
        Usuario usuario = paciente.getUsuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        // A senha não deve ser atualizada aqui, mas sim em um endpoint separado.
        
        // Nota: O UsuarioService não precisa ser chamado para o update do Usuario, pois o save
        // do Paciente dentro da transação fará o update cascata no Usuario (depende da configuração do @OneToOne).
        
        return toResponseDTO(pacienteRepository.save(paciente));
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE BUSCA E LISTAGEM
    // -----------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------
    // MÉTODO DE REMOÇÃO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public void remover(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para remoção."));
        
        // Nota: Dependendo das regras de CASCADE no seu modelo, a remoção do Paciente pode
        // ou não remover o Usuario associado. Aqui, vamos garantir a remoção do Usuario.
        Long usuarioId = paciente.getUsuario().getId();
        
        pacienteRepository.delete(paciente);
        usuarioService.remover(usuarioId); // Remove o registro de login também
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: DTO Mappers
    // -----------------------------------------------------------------------------------

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