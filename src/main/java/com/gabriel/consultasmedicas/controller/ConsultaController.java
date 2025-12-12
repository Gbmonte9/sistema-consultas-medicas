package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.ConsultaAgendamentoDTO; 
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO; 
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
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

    public ConsultaController(IConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaAgendamentoDTO requestDTO) {
        ConsultaResponseDTO response = consultaService.agendar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
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