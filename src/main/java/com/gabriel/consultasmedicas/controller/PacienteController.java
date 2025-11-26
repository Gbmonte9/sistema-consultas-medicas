package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.PacienteRequestDTO;
import com.gabriel.consultasmedicas.dto.PacienteResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor as rotas da API para gerenciamento de Pacientes.
 * Inclui operações CRUD (Criar, Ler, Atualizar, Deletar).
 */
@RestController
@RequestMapping("/api/pacientes") // Rota base corrigida para o padrão REST /api/recurso
public class PacienteController {

    private final IPacienteService pacienteService;

    // Injeção via construtor
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // -----------------------------------------------------------------------------------
    // OPERAÇÕES CRUD
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para registrar um novo paciente.
     * @param requestDTO Dados do paciente a ser criado.
     * @return 201 CREATED com o DTO do paciente criado.
     */
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criar(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        PacienteResponseDTO response = pacienteService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201
    }

    /**
     * Endpoint para buscar um paciente pelo ID.
     * @param id ID do paciente.
     * @return 200 OK com o DTO do paciente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        PacienteResponseDTO response = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(response); // Status 200
    }

    /**
     * Endpoint para listar todos os pacientes cadastrados.
     * @return 200 OK com a lista de pacientes.
     */
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<PacienteResponseDTO> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes); // Status 200 OK
    }

    /**
     * Endpoint para atualizar os dados de um paciente.
     * @param id ID do paciente a ser atualizado.
     * @param requestDTO Dados de atualização.
     * @return 200 OK com o paciente atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO requestDTO) {
        PacienteResponseDTO response = pacienteService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para remover/inativar um paciente pelo ID.
     * @param id ID do paciente a ser removido.
     * @return 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pacienteService.remover(id);
        return ResponseEntity.noContent().build(); // Status 204
    }
}