package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.UsuarioRequestDTO; // DTO para criar
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO; // DTO para retorno
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final IUsuarioService usuarioService;

    // Injeção via construtor
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /usuarios -> Cria novo usuário (Admin)
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        // O Service cuida da criptografia da senha e do salvamento.
        UsuarioResponseDTO response = usuarioService.criar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201
    }

    // GET /medicos -> Lista médicos (Público/Otimização de Rota)
    @GetMapping("/medicos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarMedicos() {
        // O Service busca apenas usuários com TipoUsuario.MEDICO
        List<UsuarioResponseDTO> medicos = usuarioService.buscarPorTipo("MEDICO");
        return ResponseEntity.ok(medicos); // Status 200
    }

    // GET /pacientes -> Lista pacientes (Admin/Otimização de Rota)
    @GetMapping("/pacientes")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPacientes() {
        // O Service busca apenas usuários com TipoUsuario.PACIENTE
        List<UsuarioResponseDTO> pacientes = usuarioService.buscarPorTipo("PACIENTE");
        return ResponseEntity.ok(pacientes); // Status 200
    }
}