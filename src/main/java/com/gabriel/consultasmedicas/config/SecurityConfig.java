package com.gabriel.consultasmedicas.config;

import com.gabriel.consultasmedicas.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração principal do Spring Security.
 * Define regras de acesso, gerenciamento de sessão e integração JWT.
 */
@Configuration
@EnableWebSecurity // Habilita o Spring Security
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Injeção via construtor
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Define a cadeia de filtros de segurança.
     * @param http Objeto HttpSecurity para configurar a segurança web.
     * @return O SecurityFilterChain configurado.
     * @throws Exception Se houver erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desabilita a proteção CSRF, pois estamos usando JWT (Stateless)
            .csrf(AbstractHttpConfigurer::disable)

            // 2. Define as regras de autorização para cada requisição
            .authorizeHttpRequests(authorize -> authorize
                // Rotas Públicas (acessíveis sem token)
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll() // Login JWT
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll() // Registro de usuário

                // Rotas Protegidas (requerem token)
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Exemplo: Acesso total para ADMIN
                .requestMatchers(HttpMethod.GET, "/api/medicos/**").permitAll() // Médicos públicos para agendamento
                .anyRequest().authenticated() // Todas as outras requisições devem estar autenticadas
            )

            // 3. Gerenciamento de Sessão: Define como Stateless (sem sessão)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 4. Configuração do Provedor de Autenticação (carrega usuário e verifica senha)
            .authenticationProvider(authenticationProvider)

            // 5. Adiciona o filtro JWT antes do filtro padrão de login/senha
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}