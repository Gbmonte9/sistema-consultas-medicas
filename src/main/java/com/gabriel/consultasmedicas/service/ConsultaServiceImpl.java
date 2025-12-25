package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<ConsultaResponseDTO> buscarAgendaDoDia(UUID id) {
        Medico medico = buscarMedicoPorIdOuUsuarioId(id);
        
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = LocalDate.now().atTime(LocalTime.MAX);

        return consultaRepository.findAgendaDoDia(medico.getId(), inicioDia, fimDia, StatusConsulta.AGENDADA)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> buscarEstatisticasDash(UUID id) {
        Medico medico = buscarMedicoPorIdOuUsuarioId(id);
        
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = LocalDate.now().atTime(LocalTime.MAX);

        Map<String, Long> stats = new HashMap<>();

        long hojeCount = consultaRepository.countByMedicoIdAndDataHoraBetween(medico.getId(), inicioDia, fimDia);

        long atendidosTotal = consultaRepository.countByMedicoIdAndStatus(medico.getId(), StatusConsulta.REALIZADA);

        long canceladasTotal = consultaRepository.countByMedicoIdAndStatus(medico.getId(), StatusConsulta.CANCELADA);

        stats.put("consultasHoje", hojeCount);
        stats.put("pacientesAtendidos", atendidosTotal);
        stats.put("consultasCanceladas", canceladasTotal);

        return stats;
    }


    @Override
    @Transactional
    public ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto) {
        UUID pacienteUuid = UUID.fromString(dto.getPacienteId());
        UUID medicoUuid = UUID.fromString(dto.getMedicoId());
        
        Medico medico = medicoRepository.findById(medicoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
                
        Paciente paciente = pacienteRepository.findById(pacienteUuid)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(pacienteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.")));
        
        LocalDateTime dataHora = dto.getDataHora();
        LocalDateTime fimConsulta = dataHora.plusMinutes(30);

        if (dataHora.isBefore(LocalDateTime.now().plusMinutes(29))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agendamento deve ter 30min de antecedência.");
        }

        List<Consulta> conflitos = consultaRepository.checarDisponibilidade(medico.getId(), dataHora, fimConsulta);
        if (!conflitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível para este médico.");
        }

        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dataHora);
        novaConsulta.setDataFim(fimConsulta);
        novaConsulta.setStatus(StatusConsulta.AGENDADA);
        novaConsulta.setMotivo(dto.getMotivo());

        return toResponseDTO(consultaRepository.save(novaConsulta));
    }

    @Override
    @Transactional
    public ConsultaResponseDTO agendarEFinalizar(ConsultaAgendamentoDTO dto) {
        UUID pacienteUuid = UUID.fromString(dto.getPacienteId());
        UUID medicoUuid = UUID.fromString(dto.getMedicoId());

        Medico medico = medicoRepository.findById(medicoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
        Paciente paciente = pacienteRepository.findById(pacienteUuid)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(pacienteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.")));

        Consulta nova = new Consulta();
        nova.setMedico(medico);
        nova.setPaciente(paciente);
        nova.setDataHora(dto.getDataHora());
        nova.setDataFim(dto.getDataHora().plusMinutes(30));
        nova.setStatus(StatusConsulta.REALIZADA);
        nova.setMotivo(dto.getMotivo());

        return toResponseDTO(consultaRepository.save(nova));
    }


    @Override
    @Transactional
    public void cancelar(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cancelar consultas realizadas.");
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
    public ConsultaResponseDTO buscarPorId(UUID id) {
        return consultaRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));
    }

    @Override
    public List<ConsultaResponseDTO> listarPorMedicoId(UUID id) {
        Medico medico = buscarMedicoPorIdOuUsuarioId(id);
        return consultaRepository.findByMedicoId(medico.getId()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorPacienteId(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseGet(() -> pacienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.")));
        return consultaRepository.findByPacienteId(paciente.getId()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    private Medico buscarMedicoPorIdOuUsuarioId(UUID id) {
        return medicoRepository.findById(id)
                .orElseGet(() -> medicoRepository.findAll().stream()
                .filter(m -> m.getUsuario().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não localizado.")));
    }

    private ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return ConsultaResponseDTO.builder()
                .id(consulta.getId())
                .dataHora(consulta.getDataHora())
                .dataFim(consulta.getDataFim())
                .status(consulta.getStatus())
                .motivo(consulta.getMotivo())
                .medico(ConsultaResponseDTO.MedicoConsultaDTO.builder()
                        .id(consulta.getMedico().getId())
                        .nome(consulta.getMedico().getUsuario().getNome())
                        .especialidade(consulta.getMedico().getEspecialidade())
                        .crm(consulta.getMedico().getCrm()).build())
                .paciente(ConsultaResponseDTO.PacienteConsultaDTO.builder()
                        .id(consulta.getPaciente().getId())
                        .nome(consulta.getPaciente().getUsuario().getNome())
                        .cpf(consulta.getPaciente().getCpf())
                        .email(consulta.getPaciente().getUsuario().getEmail()).build())
                .build();
    }

    @Override public List<ConsultaResponseDTO> listarTodas() { return consultaRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList()); }
    @Override public List<PacienteResponseDTO> listarPacientesAtendidosPorMedico(UUID id) { Medico m = buscarMedicoPorIdOuUsuarioId(id); return consultaRepository.findDistinctPacientesByMedicoId(m.getId()).stream().map(p -> PacienteResponseDTO.builder().id(p.getId()).nomeUsuario(p.getUsuario().getNome()).emailUsuario(p.getUsuario().getEmail()).cpf(p.getCpf()).telefone(p.getTelefone()).build()).collect(Collectors.toList()); }
    @Override public List<ConsultaResponseDTO> listarPorMedicoEStatus(UUID medicoId, StatusConsulta status) { return consultaRepository.findByMedicoIdAndStatus(medicoId, status).stream().map(this::toResponseDTO).collect(Collectors.toList()); }
    @Override public List<ConsultaResponseDTO> listarPorPacienteEStatus(UUID pacienteId, StatusConsulta status) { return consultaRepository.findByPacienteIdAndStatus(pacienteId, status).stream().map(this::toResponseDTO).collect(Collectors.toList()); }
    @Override @Transactional public void remover(UUID id) { consultaRepository.deleteById(id); }
    @Override @Transactional public ConsultaResponseDTO atualizarStatus(UUID id, StatusConsulta novoStatus) { Consulta c = consultaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); c.setStatus(novoStatus); return toResponseDTO(consultaRepository.save(c)); }
}