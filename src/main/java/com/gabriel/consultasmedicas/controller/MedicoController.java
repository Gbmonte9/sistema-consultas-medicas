package com.gabriel.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import com.gabriel.consultasmedicas.model.Medico;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @Autowired
    private IMedicoService medicoService;

    @PostMapping
    public Medico salvar(@RequestBody Medico medico) {
        return medicoService.salvar(medico);
    }

    @GetMapping
    public List<Medico> listarTodos() {
        return medicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Medico buscarPorId(@PathVariable Long id) {
        return medicoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        medicoService.remover(id);
    }

}
