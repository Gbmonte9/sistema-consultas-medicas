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
import java.util.UUID; 

@RestController
@RequestMapping("/api/usuarios") 
public class UsuarioController {
    
    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping("/registrar") 
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioCadastroDTO requestDTO) {
        UsuarioResponseDTO response = usuarioService.criar(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable UUID id, @RequestBody UsuarioCadastroDTO requestDTO) {
        UsuarioResponseDTO response = usuarioService.atualizar(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/tipo/{tipo}") 
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorTipo(@PathVariable String tipo) {
        try {
            TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo.toUpperCase());
            List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorTipo(tipoUsuario);
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build(); 
    }
}