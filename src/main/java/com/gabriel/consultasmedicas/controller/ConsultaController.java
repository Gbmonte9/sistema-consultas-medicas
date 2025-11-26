package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO; // DTO para agendar (usando o DTO correto)
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO; // DTO para retorno
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
import jakarta.validation.Valid; // Necessário para validação de DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor as rotas da API para agendamento e gerenciamento de Consultas.
 * As regras de negócio complexas (horários, cancelamento) ficam na camada Service.
 */
@RestController
@RequestMapping("/api/consultas") // Rota base corrigida para o padrão REST /api/consultas
public class ConsultaController {

    private final IConsultaService consultaService;

    // Injeção via construtor
    public ConsultaController(IConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // -----------------------------------------------------------------------------------
    // OPERAÇÕES DE AGENDAMENTO
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para agendar uma nova consulta.
     * @param requestDTO Dados do agendamento (pacienteId, medicoId, dataHora).
     * @return 201 CREATED com o DTO da consulta agendada.
     */
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaAgendamentoDTO requestDTO) {
        ConsultaResponseDTO response = consultaService.agendar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201 Created
    }

    /**
     * Endpoint para buscar uma consulta pelo ID.
     * @param id ID da consulta.
     * @return 200 OK com o DTO da consulta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        ConsultaResponseDTO response = consultaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para listar todas as consultas.
     * A segurança (Spring Security) deve garantir que:
     * - ADMIN lista todas.
     * - MEDICO lista apenas as consultas dele.
     * - PACIENTE lista apenas as consultas dele.
     * @return 200 OK com a lista de consultas.
     */
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        // A lógica de filtragem/listagem está no Service e é transparente para o Controller.
        List<ConsultaResponseDTO> consultas = consultaService.listarTodas();
        return ResponseEntity.ok(consultas);
    }

    // -----------------------------------------------------------------------------------
    // OPERAÇÕES DE STATUS
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para cancelar uma consulta.
     * @param id ID da consulta a ser cancelada.
     * @return 204 NO CONTENT.
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }

    /**
     * Endpoint para finalizar (dar baixa) em uma consulta.
     * Deve ser usado após o atendimento e antes de registrar o Histórico/Prontuário.
     * @param id ID da consulta a ser finalizada.
     * @return 204 NO CONTENT.
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        consultaService.finalizar(id);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }

    /**
     * Endpoint para remover/excluir uma consulta (Uso administrativo).
     * @param id ID da consulta a ser removida.
     * @return 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        consultaService.remover(id);
        return ResponseEntity.noContent().build(); // Status 204
    }
}