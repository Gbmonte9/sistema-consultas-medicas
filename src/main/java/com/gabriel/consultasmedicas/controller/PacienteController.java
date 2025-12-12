package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.PacienteCadastroDTO;
import com.gabriel.consultasmedicas.dto.PacienteRequestDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes") 
public class PacienteController {

    private final IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criar(@Valid @RequestBody PacienteCadastroDTO requestDTO) { 
        // Certifique-se de importar o PacienteCadastroDTO
        PacienteResponseDTO response = pacienteService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        PacienteResponseDTO response = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(response); 
    }
   
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<PacienteResponseDTO> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PacienteCadastroDTO requestDTO) {
        PacienteResponseDTO response = pacienteService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pacienteService.remover(id);
        return ResponseEntity.noContent().build(); 
    }
    
}