package com.gabriel.consultasmedicas.config;

import com.gabriel.consultasmedicas.model.Usuario;
import com.gabriel.consultasmedicas.model.TipoUsuario; // Verifique se este é o nome do seu Enum de tipos
import com.gabriel.consultasmedicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UsuarioAdminLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
    	
        if (repository.findByEmail("admin@admin.com").isEmpty()) {
            
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@admin.com");
            
            admin.setSenha(passwordEncoder.encode("1234"));
            
            admin.setTipo(TipoUsuario.ADMIN); 
            
            repository.save(admin);
            
            System.out.println("\n=========================================");
            System.out.println("✅ ADMIN CRIADO COM SUCESSO!");
            System.out.println("E-mail: admin@admin.com");
            System.out.println("Senha: 1234");
            System.out.println("=========================================\n");
        } else {
            System.out.println("\nℹ️ O usuário Admin já existe no banco.\n");
        }
    }
}