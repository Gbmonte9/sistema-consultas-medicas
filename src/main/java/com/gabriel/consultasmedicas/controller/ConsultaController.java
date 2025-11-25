package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.ConsultaRequestDTO; // DTO para agendar
import com.gabriel.consultasmedicas.dto.ConsultaResponseDTO; // DTO para retorno
import com.gabriel.consultasmedicas.interfaces.IConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final IConsultaService consultaService;

    public ConsultaController(IConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // POST /consultas -> Agenda nova consulta (Paciente)
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendarConsulta(@RequestBody ConsultaRequestDTO requestDTO) {
        ConsultaResponseDTO response = consultaService.agendar(requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    // GET /consultas -> Lista todas as consultas (Médico / Paciente)
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas() {
        // A lógica de segurança filtra se lista tudo (Médico) ou só as próprias (Paciente)
        List<ConsultaResponseDTO> consultas = consultaService.listar();
        return ResponseEntity.ok(consultas);
    }

    // PUT /consultas/{id}/cancelar -> Cancela consulta (Médico / Paciente)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }
}