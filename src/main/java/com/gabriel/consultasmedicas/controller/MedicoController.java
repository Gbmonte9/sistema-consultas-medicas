package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.MedicoCadastroDTO;
import com.gabriel.consultasmedicas.dto.MedicoResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor as rotas da API para gerenciamento de Médicos.
 * Inclui operações CRUD (Criar, Ler, Atualizar, Deletar) e filtros por especialidade.
 * O registro inicial (com email/senha) é feito pelo UsuarioController.
 */
@RestController
@RequestMapping("/api/medicos") // Rota base corrigida para o padrão REST /api/recurso
public class MedicoController {

    private final IMedicoService medicoService;

    // Injeção via construtor
    public MedicoController(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // -----------------------------------------------------------------------------------
    // OPERAÇÕES CRUD
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint para registrar um novo médico.
     * OBS: Este endpoint pode ser usado por um ADMIN para criar um médico diretamente,
     * mas o fluxo padrão de cadastro do usuário é via UsuarioController.
     * @param requestDTO Dados do médico a ser criado (CRM e Especialidade).
     * @return 201 CREATED com o DTO do médico criado.
     */
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoCadastroDTO requestDTO) {
        MedicoResponseDTO response = medicoService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201
    }

    /**
     * Endpoint para buscar um médico pelo ID.
     * @param id ID do médico.
     * @return 200 OK com o DTO do médico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id) {
        MedicoResponseDTO response = medicoService.buscarPorId(id);
        return ResponseEntity.ok(response); // Status 200
    }

    /**
     * Endpoint para listar todos os médicos cadastrados.
     * Rota pública, usada na tela de agendamento de consultas.
     * @return 200 OK com a lista de médicos.
     */
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        List<MedicoResponseDTO> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos); // Status 200 OK
    }

    /**
     * Endpoint para buscar médicos por especialidade (exemplo de filtro).
     * Ex: GET /api/medicos/especialidade?nome=Cardiologia
     * @param especialidade Especialidade a ser filtrada.
     * @return 200 OK com a lista de médicos daquela especialidade.
     */
    @GetMapping("/especialidade")
    public ResponseEntity<List<MedicoResponseDTO>> buscarPorEspecialidade(@RequestParam("nome") String especialidade) {
        List<MedicoResponseDTO> medicos = medicoService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(medicos);
    }

    /**
     * Endpoint para atualizar os dados de um médico.
     * @param id ID do médico a ser atualizado.
     * @param requestDTO Dados de atualização (CRM e Especialidade).
     * @return 200 OK com o médico atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MedicoCadastroDTO requestDTO) {
        MedicoResponseDTO response = medicoService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para remover/inativar um médico pelo ID.
     * @param id ID do médico a ser removido.
     * @return 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        medicoService.remover(id);
        return ResponseEntity.noContent().build(); // Status 204
    }
}