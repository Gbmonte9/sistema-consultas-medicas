package com.gabriel.consultasmedicas.config;

import com.gabriel.consultasmedicas.model.*;
import com.gabriel.consultasmedicas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsuarioAdminLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // 1. CRIAR ADMIN DO SISTEMA
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador do Sistema");
            admin.setEmail("admin@admin.com");
            admin.setSenha(passwordEncoder.encode("1234"));
            admin.setTipo(TipoUsuario.ADMIN); 
            usuarioRepository.save(admin);
            System.out.println("✅ ADMIN CONFIGURADO.");
        }

        // 2. DADOS DOS MÉDICOS
        String[] nomesMedicos = {
            "Dr. Ricardo Oliveira", "Dra. Sandra Helena", "Dr. Marcos Vinícius", 
            "Dra. Beatriz Costa", "Dr. Juliano Mendes", "Dra. Cláudia Souza"
        };

        String[] especialidades = {
            "Psicólogo(a) Clínico", "Psicólogo(a) Infantil", "Psiquiatra", 
            "Neuropsicólogo(a)", "Psicanalista", "Terapeuta Ocupacional"
        };

        String[] emailsMedicos = {
            "ricardo.clinico@clinica.com", "sandra.infantil@clinica.com", "marcos.psiquiatra@clinica.com",
            "beatriz.neuro@clinica.com", "juliano.psicanalista@clinica.com", "claudia.terapeuta@clinica.com"
        };

        String[] crms = {
            "CRM-SP 123456", "CRM-RJ 654321", "CRM-MG 987654",
            "CRM-PR 112233", "CRM-SC 445566", "CRM-RS 778899"
        };

        // LOOP PARA CRIAÇÃO DE MÉDICOS
        for (int i = 0; i < nomesMedicos.length; i++) {
            
            // Verifica se o médico já existe pelo e-mail
            if (usuarioRepository.findByEmail(emailsMedicos[i]).isEmpty()) {
                
                // --- 1. CRIAR USUÁRIO DO MÉDICO ---
                Usuario userMed = new Usuario();
                userMed.setNome(nomesMedicos[i]);
                userMed.setEmail(emailsMedicos[i]);
                userMed.setSenha(passwordEncoder.encode("clinica123"));
                userMed.setTipo(TipoUsuario.MEDICO); 
                userMed = usuarioRepository.save(userMed);

                // --- 2. CRIAR PERFIL DO MÉDICO ---
                Medico medico = new Medico();
                medico.setCrm(crms[i]); 
                medico.setEspecialidade(especialidades[i]);
                medico.setUsuario(userMed); // Vincula ao Usuário salvo acima
                
                medicoRepository.save(medico);

                System.out.println("✅ MÉDICO CRIADO: " + nomesMedicos[i] + " | CRM: " + crms[i]);
            }
        }
    }
}