package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interfaces.IConsultaService;
import model.Consulta;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService consultaService;

    @PostMapping("/agendar")
    public Consulta agendar(@RequestBody Consulta consulta) {
        return consultaService.agendarConsulta(consulta);
    }

    @DeleteMapping("/cancelar/{id}")
    public void cancelar(@PathVariable Long id) {
        consultaService.cancelarConsulta(id);
    }

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/medico/{medicoId}")
    public List<Consulta> listarPorMedico(@PathVariable Long medicoId) {
        return consultaService.listarPorMedico(medicoId);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Consulta> listarPorPaciente(@PathVariable Long pacienteId) {
        return consultaService.listarPorPaciente(pacienteId);
    }
    
}
