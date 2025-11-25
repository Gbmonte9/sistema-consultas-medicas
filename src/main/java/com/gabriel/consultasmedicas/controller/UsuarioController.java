package com.gabriel.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.consultasmedicas.interfaces.IUsuarioService;
import com.gabriel.consultasmedicas.model.Usuario;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    @PostMapping("/login")
    public Usuario autenticar(@RequestParam String email, @RequestParam String senha) {
        return usuarioService.autenticar(email, senha);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        usuarioService.remover(id);
    }


    
}
