package com.gabriel.consultasmedicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    @GetMapping("/pacientes")
    public String pacientes() {
        return "pacientes"; // templates/pacientes.html
    }

    @GetMapping("/consultas")
    public String consultas() {
        return "consultas";
    }
}
