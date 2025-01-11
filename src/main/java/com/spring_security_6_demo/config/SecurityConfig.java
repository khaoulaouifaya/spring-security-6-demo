package com.spring_security_6_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Désactive CSRF pour simplifier (ne pas désactiver en production sans raison valable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**","/users").permitAll() // Les URL publiques accessibles sans authentification
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Accès réservé au rôle ADMIN
                        .anyRequest().authenticated()); // Toutes les autres requêtes nécessitent une authentification
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // URL de la page de connexion personnalisée
//                        .permitAll() // Autorise tout le monde à accéder à la page de connexion
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL pour déconnexion
//                        .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
//                        .permitAll()
//                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
