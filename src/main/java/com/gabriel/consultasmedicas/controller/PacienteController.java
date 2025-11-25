package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IPacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pacientes") // Rota base: /pacientes
public class PacienteController {
    
    private final IPacienteService pacienteService;

    // Injeção via construtor
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // GET /pacientes -> Lista pacientes cadastrados (Admin)
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        // O Service busca todos os Pacientes e os mapeia para o DTO
        List<PacienteResponseDTO> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes); // Status 200 OK
    }
}