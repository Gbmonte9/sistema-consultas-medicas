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
     * Lista as consultas de um paciente. 
     * O Service já está tratando se o ID é de Usuário ou de Paciente.
     */
    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorPaciente(@PathVariable UUID id) {
        List<ConsultaResponseDTO> consultas = consultaService.listarPorPacienteId(id);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Agendamento padrão de consulta.
     */
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaAgendamentoDTO requestDTO) {
        ConsultaResponseDTO response = consultaService.agendar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Agendar e Finalizar (para registros retroativos ou rápidos).
     * Atualizado para incluir o campo 'motivo' na conversão do DTO.
     */
    @PostMapping("/agendar-e-finalizar")
    public ResponseEntity<ConsultaResponseDTO> agendarEFinalizar(
            @RequestBody @Valid ConsultaHistoricoIntegradoDTO dto) {

        // Criamos o DTO de agendamento incluindo o campo motivo (vazio ou vindo do DTO integrado)
        ConsultaAgendamentoDTO consultaAgendamentoDTO = new ConsultaAgendamentoDTO();
        consultaAgendamentoDTO.setPacienteId(dto.pacienteId().toString());
        consultaAgendamentoDTO.setMedicoId(dto.medicoId().toString());
        consultaAgendamentoDTO.setDataHora(dto.dataHora());
        consultaAgendamentoDTO.setMotivo("Consulta agendada e finalizada pelo sistema"); // Ou dto.motivo() se existir no HistoricoIntegrado

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