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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/historicos") // Alterado para plural para bater com o frontend
public class HistoricoController {

    private final IHistoricoService historicoService;

    public HistoricoController(IHistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @PostMapping
    public ResponseEntity<HistoricoResponseDTO> registrar(@Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.registrarHistorico(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorId(@PathVariable UUID id) {
        HistoricoResponseDTO response = historicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // --- NOVO MÉTODO PARA O PRONTUÁRIO DO PACIENTE ---
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoricoResponseDTO>> listarPorPaciente(@PathVariable UUID pacienteId) {
        List<HistoricoResponseDTO> response = historicoService.buscarPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorConsultaId(@PathVariable UUID consultaId) {
        HistoricoResponseDTO response = historicoService.buscarPorConsultaId(consultaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        historicoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pdf/consultas")
    public ResponseEntity<byte[]> gerarHistoricoConsultasPDF() {
        byte[] pdfBytes = historicoService.gerarHistoricoConsultasPDF();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"historico_consultas.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}