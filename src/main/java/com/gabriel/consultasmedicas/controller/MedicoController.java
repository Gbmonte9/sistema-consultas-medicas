package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID; // 🚨 NOVO: Importação do UUID

@RestController
@RequestMapping("/api/medicos") 
public class MedicoController {

    private final IMedicoService medicoService;

    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoCadastroDTO requestDTO) {
        // Assume que MedicoCadastroDTO lida corretamente com as referências (ex: usuarioId) como UUID, se necessário.
        MedicoResponseDTO response = medicoService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }


    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable UUID id) {
        MedicoResponseDTO response = medicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        List<MedicoResponseDTO> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos); 
    }

    @GetMapping("/especialidade")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorEspecialidade(@RequestParam("nome") String especialidade) {
        List<MedicoResponseDTO> medicos = medicoService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(medicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody MedicoCadastroDTO requestDTO) {
        MedicoResponseDTO response = medicoService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        medicoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}