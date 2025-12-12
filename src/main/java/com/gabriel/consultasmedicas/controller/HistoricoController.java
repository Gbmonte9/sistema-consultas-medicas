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

@RestController
@RequestMapping("/api/historico") 
public class HistoricoController {

    private final IHistoricoService historicoService;

    public HistoricoController(IHistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @PostMapping
    public ResponseEntity<HistoricoResponseDTO> registrar(@Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.registrarHistorico(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorId(@PathVariable Long id) {
        HistoricoResponseDTO response = historicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<HistoricoResponseDTO> buscarPorConsultaId(@PathVariable Long consultaId) {
        HistoricoResponseDTO response = historicoService.buscarPorConsultaId(consultaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody HistoricoRequestDTO requestDTO) {
        HistoricoResponseDTO response = historicoService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        historicoService.remover(id);
        return ResponseEntity.noContent().build(); // Status 204
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