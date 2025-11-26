package com.gabriel.consultasmedicas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração MVC para a aplicação.
 * Mais importante: Habilita e configura o CORS (Cross-Origin Resource Sharing)
 * para permitir requisições do frontend para a API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura as regras de CORS.
     * @param registry O registro de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica o CORS a todos os endpoints da API
                .allowedOrigins("*") // Permite requisições de qualquer origem (ideal para desenvolvimento)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite métodos HTTP comuns
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(false) // Não requer credenciais/cookies para esta configuração
                .maxAge(3600); // Tempo de cache da pré-verificação (preflight) em segundos
    }
}