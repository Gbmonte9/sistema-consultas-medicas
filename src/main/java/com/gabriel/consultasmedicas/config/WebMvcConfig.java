package com.gabriel.consultasmedicas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Mapeia a URL base "/" diretamente para o arquivo login.html
        // Isso garante que ao acessar http://localhost:8080/ o usuário veja a tela de login.
        registry.addViewController("/").setViewName("login.html");
        
        // Mapeia o endpoint "/login" do Spring Security diretamente para o login.html.
        // Isso é crucial para evitar o loop de redirecionamento.
        registry.addViewController("/login").setViewName("login.html");
    }
}