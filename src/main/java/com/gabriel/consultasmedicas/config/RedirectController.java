package com.gabriel.consultasmedicas.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    
    // 1. URL Base: REMOVIDO para evitar o loop de redirecionamento.
    // O Spring Security agora lida com a rota raiz ("/") e direciona
    // para a página de login se o usuário não estiver autenticado.
    
    // 2. Página de Cadastro de Usuário
    @GetMapping("/view/cadastro-usuario")
    public String exibirCadastroUsuario() {
        return "forward:/cadastro-usuario.html";
    }

    // 3. Página de Listagem de Médicos (Acesso Público)
    @GetMapping("/view/medicos")
    public String exibirListaMedicos() {
        return "forward:/medicos.html";
    }

    // 4. Página de Listagem de Pacientes (Acesso Admin)
    @GetMapping("/view/pacientes")
    public String exibirListaPacientes() {
        return "forward:/pacientes.html";
    }

    // 5. Página de Agendamento/Listagem de Consultas
    @GetMapping("/view/consultas")
    public String exibirConsultas() {
        return "forward:/consultas.html";
    }

    // 6. Página de Histórico/Relatórios
    @GetMapping("/view/historico")
    public String exibirHistorico() {
        return "forward:/historico.html";
    }
}