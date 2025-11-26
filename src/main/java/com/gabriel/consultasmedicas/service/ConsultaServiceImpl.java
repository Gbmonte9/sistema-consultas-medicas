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
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    // -----------------------------------------------------------------------------------
    // MÉTODO PRINCIPAL: AGENDAMENTO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public ConsultaResponseDTO agendar(ConsultaAgendamentoDTO dto) {
        // 1. Validação de Entidades (Médico e Paciente)
        Medico medico = medicoRepository.findById(dto.getMedicoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
        
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));

        // 2. Regra de Negócio: Validação de Data/Hora
        LocalDateTime dataHora = dto.getDataHora();
        
        // Exemplo: Consultas devem ser agendadas com pelo menos 30 minutos de antecedência
        if (dataHora.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A consulta deve ser agendada com no mínimo 30 minutos de antecedência.");
        }

        // 3. Regra de Negócio: Verificação de Disponibilidade do Médico
        // Assumindo que a duração padrão da consulta é 30 minutos
        LocalDateTime fimConsulta = dataHora.plusMinutes(30);

        Optional<Consulta> consultaExistente = consultaRepository.checarDisponibilidade(
            medico.getId(), 
            dataHora, 
            fimConsulta
        );

        if (consultaExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O médico já possui uma consulta marcada para este horário.");
        }
        
        // 4. Mapeamento DTO para Entidade
        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(dataHora);
        novaConsulta.setStatus(StatusConsulta.AGENDADA); // Status inicial
        
        // 5. Salva a consulta
        Consulta consultaSalva = consultaRepository.save(novaConsulta);

        // 6. Retorna o DTO de Resposta
        return toResponseDTO(consultaSalva);
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE MANIPULAÇÃO DE STATUS
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public ConsultaResponseDTO atualizarStatus(Long id, StatusConsulta novoStatus) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));

        // Regra de Negócio: Validação de transição de status (Exemplo: não pode ir de CANCELADA para AGENDADA)
        // Implementar lógica de transição se necessário. Por simplicidade, permitimos.
        
        consulta.setStatus(novoStatus);
        
        return toResponseDTO(consultaRepository.save(consulta));
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada para cancelamento."));

        // Regra de Negócio: Não permite cancelar consultas já realizadas
        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consultas já realizadas não podem ser canceladas.");
        }
        
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }
    
    // -----------------------------------------------------------------------------------
    // MÉTODOS DE BUSCA E LISTAGEM
    // -----------------------------------------------------------------------------------

    @Override
    public ConsultaResponseDTO buscarPorId(Long id) {
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
    public List<ConsultaResponseDTO> listarPorMedicoId(Long medicoId) {
        List<Consulta> consultas = consultaRepository.findByMedicoId(medicoId);
        return consultas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResponseDTO> listarPorPacienteId(Long pacienteId) {
        List<Consulta> consultas = consultaRepository.findByPacienteId(pacienteId);
        return consultas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: DTO Mappers
    // -----------------------------------------------------------------------------------

    private ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return ConsultaResponseDTO.builder()
            .id(consulta.getId())
            .dataHora(consulta.getDataHora())
            .status(consulta.getStatus())
            .medicoId(consulta.getMedico().getId())
            .pacienteId(consulta.getPaciente().getId())
            .nomeMedico(consulta.getMedico().getUsuario().getNome())
            .nomePaciente(consulta.getPaciente().getUsuario().getNome())
            .build();
    }

	@Override
	public List<ConsultaResponseDTO> listarPorMedico(Long medicoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaResponseDTO> listarPorPaciente(Long pacienteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaResponseDTO> listarPorMedicoEStatus(Long medicoId, StatusConsulta status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaResponseDTO> listarPorPacienteEStatus(Long pacienteId, StatusConsulta status) {
		// TODO Auto-generated method stub
		return null;
	}
}