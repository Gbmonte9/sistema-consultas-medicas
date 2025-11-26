package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.dto.UsuarioCadastroDTO;
import com.gabriel.consultasmedicas.dto.UsuarioResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.TipoUsuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor as rotas da API para gerenciamento de Usuários.
 * Contém a rota pública de registro e rotas seguras para auditoria e CRUD completo de Usuários.
 */
@RestController
@RequestMapping("/api/usuarios") // USAR PADRÃO /api/recurso
public class UsuarioController {
    
    private final IUsuarioService usuarioService;

    // Injeção via construtor
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // -----------------------------------------------------------------------------------
    // ROTA PÚBLICA: REGISTRO (Utilizada por Paciente/Médico para criar o login)
    // -----------------------------------------------------------------------------------

    /**
     * Endpoint de Registro. Cria o registro de login.
     * Esta rota é configurada como pública no SecurityConfig.
     * @param requestDTO Dados de nome, email, senha e tipo (PACIENTE/MEDICO).
     * @return Retorna o DTO do usuário criado.
     */
    @PostMapping("/registrar") // Rota explícita para registro
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioCadastroDTO requestDTO) {
        // O @Valid garante que as regras do DTO sejam verificadas.
        UsuarioResponseDTO response = usuarioService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Status 201
    }

    // -----------------------------------------------------------------------------------
    // ROTAS SEGURAS (GERALMENTE PARA ADMIN/AUDITORIA)
    // -----------------------------------------------------------------------------------

    /**
     * Busca todos os usuários por tipo (exige autenticação).
     * Exemplo: GET /api/usuarios/tipo/MEDICO
     * @param tipo Tipo de usuário a ser buscado (ex: "PACIENTE", "MEDICO").
     * @return Lista de usuários.
     */
    @GetMapping("/tipo/{tipo}") // Rota mais RESTful para buscar por atributo
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorTipo(@PathVariable String tipo) {
        // Converte a string do path para o Enum TipoUsuario
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo.toUpperCase());
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorTipo(tipoUsuario);
        return ResponseEntity.ok(usuarios); // Status 200
    }

    /**
     * Busca todos os usuários (Admin/Auditoria).
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios); // Status 200
    }

    /**
     * Busca um usuário pelo ID (Admin/Auditoria).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}