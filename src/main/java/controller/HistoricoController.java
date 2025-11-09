package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interfaces.IHistoricoService;
import model.Historico;

import java.util.List;

@RestController
@RequestMapping("/historicos")
public class HistoricoController {
    
    @Autowired
    private IHistoricoService historicoService;

    @PostMapping
    public Historico salvar(@RequestBody Historico historico) {
        return historicoService.salvar(historico);
    }

    @PutMapping("/{id}")
    public Historico atualizar(@PathVariable Long id, @RequestBody Historico historico) {
        return historicoService.atualizar(id, historico);
    }

    @GetMapping
    public List<Historico> listarTodos() {
        return historicoService.listarTodos();
    }

    @GetMapping("/consulta/{consultaId}")
    public List<Historico> listarPorConsulta(@PathVariable Long consultaId) {
        return historicoService.listarPorConsulta(consultaId);
    }

    @GetMapping("/{id}")
    public Historico buscarPorId(@PathVariable Long id) {
        return historicoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        historicoService.remover(id);
    }

}

 
