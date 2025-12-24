package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaHistoricoIntegradoDTO;
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO;
import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
import com.gabriel.consultasmedicas.interfaces.IHistoricoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final IConsultaService consultaService;
    private final IHistoricoService historicoService; 

    public ConsultaController(IConsultaService consultaService, IHistoricoService historicoService) {
        this.consultaService = consultaService;
        this.historicoService = historicoService;
    }

    /**
     * DASHBOARD: Lista a agenda do dia (Hoje) para um médico específico.
     * Resolve o erro 404 em consultasService.js:44
     */
    @GetMapping("/medico/{id}/hoje")
    public ResponseEntity<List<ConsultaResponseDTO>> listarAgendaDoDia(@PathVariable UUID id) {
        List<ConsultaResponseDTO> agenda = consultaService.buscarAgendaDoDia(id);
        return ResponseEntity.ok(agenda);
    }

    /**
     * DASHBOARD: Retorna contadores de consultas (Hoje, Atendidos, Cancelados).
     * Resolve o erro 404 em consultasService.js:59
     */
    @GetMapping("/medico/{id}/estatisticas")
    public ResponseEntity<Map<String, Long>> buscarEstatisticas(@PathVariable UUID id) {
        Map<String, Long> stats = consultaService.buscarEstatisticasDash(id);
        return ResponseEntity.ok(stats);
    }

    /**
     * Lista os pacientes ÚNICOS atendidos por um médico.
     */
    @GetMapping("/medico/{id}/pacientes")
    public ResponseEntity<List<?>> listarPacientesPorMedico(@PathVariable UUID id) {
        var pacientes = consultaService.listarPacientesAtendidosPorMedico(id);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Lista as consultas de um médico.
     */
    @GetMapping("/medico/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorMedico(@PathVariable UUID id) {
        List<ConsultaResponseDTO> consultas = consultaService.listarPorMedicoId(id);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Lista as consultas de um paciente. 
     */
    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorPaciente(@PathVariable UUID id) {
        List<ConsultaResponseDTO> consultas = consultaService.listarPorPacienteId(id);
        return ResponseEntity.ok(consultas);
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaAgendamentoDTO requestDTO) {
        ConsultaResponseDTO response = consultaService.agendar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/agendar-e-finalizar")
    public ResponseEntity<ConsultaResponseDTO> agendarEFinalizar(
            @RequestBody @Valid ConsultaHistoricoIntegradoDTO dto) {

        ConsultaAgendamentoDTO consultaAgendamentoDTO = new ConsultaAgendamentoDTO();
        consultaAgendamentoDTO.setPacienteId(dto.pacienteId().toString());
        consultaAgendamentoDTO.setMedicoId(dto.medicoId().toString());
        consultaAgendamentoDTO.setDataHora(dto.dataHora());
        consultaAgendamentoDTO.setMotivo("Consulta agendada e finalizada pelo sistema");

        ConsultaResponseDTO consultaFinalizadaDTO = consultaService.agendarEFinalizar(consultaAgendamentoDTO);

        HistoricoRequestDTO historicoCriacaoDTO = new HistoricoRequestDTO(
                consultaFinalizadaDTO.getId(), 
                dto.observacoes(),
                dto.receita()
        );

        historicoService.registrarHistorico(historicoCriacaoDTO);

        return new ResponseEntity<>(consultaFinalizadaDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable UUID id) {
        ConsultaResponseDTO response = consultaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        List<ConsultaResponseDTO> consultas = consultaService.listarTodas();
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable UUID id) {
        consultaService.finalizar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        consultaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}