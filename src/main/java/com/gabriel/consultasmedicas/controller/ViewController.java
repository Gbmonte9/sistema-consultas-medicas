package com.gabriel.consultasmedicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String exibirPaginaInicial() {
        // retorna src/main/resources/templates/index.html
        return "index";
    }

    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; // retorna login.html
    }

    @GetMapping("/dashboard")
    public String exibirDashboard() {
        return "dashboard"; // retorna dashboard.html
    }
}
