package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IHistoricoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por gerenciar os Históricos de Consultas (Prontuários).
 * As rotas de CRUD são usadas pelo Médico (POST/PUT) e rotas de busca/PDF (GET)
 * são usadas por Médicos e Administradores.
 */
@RestController
@RequestMapping("/api/historico") // Rota base corrigida para o padrão REST /api/historico
public class HistoricoController {

    private final IHistoricoService historicoService;

    public HistoricoController(IHistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    // -----------------------------------------------------------------------------------
    // OPERAÇÕES DE PRONTUÁRIO (CRUD)
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para registrar um novo histórico (prontuário) de uma consulta realizada.
     * Usado pelo Médico.
     * @param requestDTO Dados do prontuário (consultaId, observacoes, receita).
     * @return 201 CREATED com o DTO do histórico criado.
     */
    @PostMapping
    public ResponseEntity<HistoricoResponseDTO> registrar(@Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.registrarHistorico(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201
    }

    /**
     * Endpoint para buscar um histórico (prontuário) pelo ID.
     * @param id ID do histórico.
     * @return 200 OK com o DTO do histórico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorId(@PathVariable Long id) {
        HistoricoResponseDTO response = historicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para buscar o histórico associado a uma consulta específica (Assumindo 1:1).
     * @param consultaId ID da consulta.
     * @return 200 OK com o DTO do histórico.
     */
    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorConsultaId(@PathVariable Long consultaId) {
        HistoricoResponseDTO response = historicoService.buscarPorConsultaId(consultaId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para atualizar um histórico existente (caso precise de correção).
     * Usado pelo Médico ou Admin.
     * @param id ID do histórico a ser atualizado.
     * @param requestDTO Dados de atualização.
     * @return 200 OK com o histórico atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para remover/excluir um histórico.
     * @param id ID do histórico a ser removido.
     * @return 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        historicoService.remover(id);
        return ResponseEntity.noContent().build(); // Status 204
    }

    // -----------------------------------------------------------------------------------
    // GERAÇÃO DE RELATÓRIOS (PDF)
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para gerar um relatório em PDF com o histórico completo de consultas.
     * Usado tipicamente pelo Admin.
     * @return 200 OK com o arquivo PDF.
     */
    @GetMapping("/pdf/consultas") // Rota mais específica para download de PDF
    public ResponseEntity<byte[]> gerarHistoricoConsultasPDF() {

        // 1. O Service busca os dados e usa PDFBox (ou similar) para gerar o PDF.
        byte[] pdfBytes = historicoService.gerarHistoricoConsultasPDF();

        // 2. Configura os cabeçalhos para que o navegador baixe o arquivo como PDF.
        return ResponseEntity.ok()
                // Define o nome do arquivo que será baixado
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"historico_consultas.pdf\"")
                // Define o tipo de conteúdo como PDF
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}