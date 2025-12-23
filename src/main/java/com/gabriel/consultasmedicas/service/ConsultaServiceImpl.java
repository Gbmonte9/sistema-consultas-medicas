package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.Medico;
import com.gabriel.consultasmedicas.model.Paciente;
import com.gabriel.consultasmedicas.model.StatusConsulta;
import com.gabriel.consultasmedicas.repository.ConsultaRepository;
import com.gabriel.consultasmedicas.repository.MedicoRepository;
import com.gabriel.consultasmedicas.repository.PacienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository,
                               MedicoRepository medicoRepository,
                               PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional
    public ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto) {
        UUID pacienteUuid;
        UUID medicoUuid;
        try {
            pacienteUuid = UUID.fromString(dto.getPacienteId());
            medicoUuid = UUID.fromString(dto.getMedicoId());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de ID de Médico ou Paciente inválido.");
        }
        
        Medico medico = medicoRepository.findById(medicoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
                
        Paciente paciente = pacienteRepository.findById(pacienteUuid)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(pacienteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.")));
        
        LocalDateTime dataHora = dto.getDataHora();
        LocalDateTime fimConsulta = dataHora.plusMinutes(30);

        if (dataHora.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A consulta deve ser agendada com no mínimo 30 minutos de antecedência.");
        }

        List<Consulta> conflitos = consultaRepository.checarDisponibilidade(medico.getId(), dataHora, fimConsulta);
        if (!conflitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O médico já possui uma consulta marcada para este horário.");
        }

        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dataHora);
        novaConsulta.setDataFim(fimConsulta);
        novaConsulta.setStatus(StatusConsulta.AGENDADA);
        novaConsulta.setMotivo(dto.getMotivo()); // <-- SALVANDO O MOTIVO

        Consulta consultaSalva = consultaRepository.save(novaConsulta);
        return toResponseDTO(consultaSalva);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO agendarEFinalizar(ConsultaAgendamentoDTO dto) {
        UUID pacienteUuid;
        UUID medicoUuid;
        try {
            pacienteUuid = UUID.fromString(dto.getPacienteId());
            medicoUuid = UUID.fromString(dto.getMedicoId());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de ID de Médico ou Paciente inválido.");
        }
        
        Medico medico = medicoRepository.findById(medicoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
                
        Paciente paciente = pacienteRepository.findById(pacienteUuid)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(pacienteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.")));
        
        LocalDateTime dataHora = dto.getDataHora();
        LocalDateTime fimConsulta = dataHora.plusMinutes(30);

        List<Consulta> conflitos = consultaRepository.checarDisponibilidade(medico.getId(), dataHora, fimConsulta);
        if (!conflitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O médico já possui uma consulta marcada para este horário.");
        }

        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dataHora);
        novaConsulta.setDataFim(fimConsulta);
        novaConsulta.setStatus(StatusConsulta.REALIZADA); 
        novaConsulta.setMotivo(dto.getMotivo()); // <-- SALVANDO O MOTIVO

        Consulta consultaSalva = consultaRepository.save(novaConsulta);
        return toResponseDTO(consultaSalva);
    }
    
    @Override
    @Transactional
    public void cancelar(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
        
        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consultas já realizadas não podem ser canceladas.");
        }
        
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    @Override
    @Transactional
    public void finalizar(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
        consulta.setStatus(StatusConsulta.REALIZADA);    
        consultaRepository.save(consulta);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO atualizarStatus(UUID id, StatusConsulta novoStatus) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
        consulta.setStatus(novoStatus);
        return toResponseDTO(consultaRepository.save(consulta));
    }

    @Override
    public ConsultaResponseDTO buscarPorId(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
        return toResponseDTO(consulta);
    }

    @Override
    public List<ConsultaResponseDTO> listarTodas() {
        return consultaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorMedicoId(UUID medicoId) {
        return consultaRepository.findByMedicoId(medicoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorPacienteId(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para o ID: " + id)));

        return consultaRepository.findByPacienteId(paciente.getId()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorMedicoEStatus(UUID medicoId, StatusConsulta status) {
        return consultaRepository.findByMedicoIdAndStatus(medicoId, status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorPacienteEStatus(UUID pacienteId, StatusConsulta status) {
        return consultaRepository.findByPacienteIdAndStatus(pacienteId, status).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remover(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada para remoção."));
        consultaRepository.delete(consulta);
    }

    private ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        ConsultaResponseDTO.MedicoConsultaDTO medicoDTO = ConsultaResponseDTO.MedicoConsultaDTO.builder()
                .id(consulta.getMedico().getId())
                .nome(consulta.getMedico().getUsuario().getNome())
                .especialidade(consulta.getMedico().getEspecialidade())
                .crm(consulta.getMedico().getCrm())
                .build();

        ConsultaResponseDTO.PacienteConsultaDTO pacienteDTO = ConsultaResponseDTO.PacienteConsultaDTO.builder()
                .id(consulta.getPaciente().getId())
                .nome(consulta.getPaciente().getUsuario().getNome())
                .cpf(consulta.getPaciente().getCpf())
                .build();

        return ConsultaResponseDTO.builder()
                .id(consulta.getId())
                .dataHora(consulta.getDataHora())
                .dataFim(consulta.getDataFim())    
                .status(consulta.getStatus())
                .motivo(consulta.getMotivo())
                .medico(medicoDTO)
                .paciente(pacienteDTO)
                .build();
    }
}