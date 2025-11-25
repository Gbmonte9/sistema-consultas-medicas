package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicos") // Rota base: /medicos
public class MedicoController {
    
    private final IMedicoService medicoService;

    // Injeção via construtor
    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // GET /medicos -> Lista médicos cadastrados (Público)
    @GetMapping 
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        // O Service busca todos os Médicos e os mapeia para o DTO
        List<MedicoResponseDTO> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos); // Status 200 OK
    }
    
    // Se precisar de uma rota de criação exclusiva para Médicos, ficaria aqui:
    // @PostMapping
    // public ResponseEntity<MedicoResponseDTO> criarMedico(@RequestBody MedicoRequestDTO requestDTO) { ... }
}