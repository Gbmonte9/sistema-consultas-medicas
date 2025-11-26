package com.gabriel.consultasmedicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    
    // Esta classe mapeia URLs de View com o prefixo "/view/" 
    // e usa "forward:" para servir o arquivo HTML estático correspondente.
    // As rotas "/" e "/login" são tratadas pelo WebMvcConfig.java.
    
    // 1. Página de Cadastro de Usuário
    @GetMapping("/view/cadastro-usuario")
    public String exibirCadastroUsuario() {
        return "forward:/cadastro-usuario.html";
    }

    // 2. Página de Listagem de Médicos
    @GetMapping("/view/medicos")
    public String exibirListaMedicos() {
        return "forward:/medicos.html";
    }

    // 3. Página de Listagem de Pacientes
    @GetMapping("/view/pacientes")
    public String exibirListaPacientes() {
        return "forward:/pacientes.html";
    }

    // 4. Página de Agendamento/Listagem de Consultas
    @GetMapping("/view/consultas")
    public String exibirConsultas() {
        return "forward:/consultas.html";
    }

    // 5. Página de Histórico/Relatórios
    @GetMapping("/view/historico")
    public String exibirHistorico() {
        return "forward:/historico.html";
    }
}