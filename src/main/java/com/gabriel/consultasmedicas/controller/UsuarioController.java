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
    
    @GetMapping("/tipo/{tipo}") 
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorTipo(@PathVariable String tipo) {
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo.toUpperCase());
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorTipo(tipoUsuario);
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios); 
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build(); 
    }
    
}