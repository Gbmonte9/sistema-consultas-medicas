package com.gabriel.consultasmedicas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desativa CSRF para simplificar o trabalho com APIs REST e formulários customizados.
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests(auth -> auth
                // Permite acesso a todos os arquivos estáticos e à página de erro
                .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/js/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/img/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/erro.html")).permitAll()
                
                // Rotas de acesso público (Home, Login e o arquivo HTML)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/login.html")).permitAll() // Crucial para arquivos estáticos
                
                // Rotas de View Públicas (Cadastro)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/view/cadastro-usuario")).permitAll()
                
                // Rotas da API de autenticação e cadastro (públicas)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/usuarios")).permitAll() 
                
                // Todas as outras requisições requerem autenticação
                .anyRequest().authenticated()
            )
            
            // Configura o formulário de login do Spring Security
            .formLogin(form -> form
                .loginPage("/login") // Endpoint que o Spring Security deve usar
                .permitAll()
            );

        return http.build();
    }
}