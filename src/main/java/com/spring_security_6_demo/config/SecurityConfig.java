package com.spring_security_6_demo.config;

import com.spring_security_6_demo.entities.User;
import com.spring_security_6_demo.filters.JwtAuthenticationFilter;
import com.spring_security_6_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SecurityConfig {
//    @Autowired
//    @Lazy
//    private UserService service; // Assurez-vous que UserService implémente UserDetailsService
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // Utilisez votre service personnalisé pour charger l'utilisateur
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        return username -> {
//            User user = service.loadUserByUsername(username); // Une méthode personnalisée de service qui charge un utilisateur par nom
//            if (user == null) {
//                throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
//            }
//            user.getRoles().forEach(role -> {
//                authorities.add(new SimpleGrantedAuthority(role.getRole()));
//            });
//            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authorities);
//        };
//    }
//
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Désactive CSRF pour simplifier (ne pas désactiver en production sans raison valable)
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Spécifie une gestion de session stateless
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**","/login").permitAll() // Les URL publiques accessibles sans authentification
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Accès réservé au rôle ADMIN
                        .anyRequest().authenticated());// Toutes les autres requêtes nécessitent une authentification
//                .formLogin(formLogin -> formLogin // Configuration explicite pour formLogin
//                        .defaultSuccessUrl("/") // URL de redirection après connexion réussie
//                        .failureUrl("/login?error") // URL en cas de connexion échouée
//                        .permitAll());
//                .formLogin(form -> form
//                        .loginPage("/login") // URL de la page de connexion personnalisée
//                        .permitAll() // Autorise tout le monde à accéder à la page de connexion
//                );
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL pour déconnexion
//                        .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
//                        .permitAll()
//                );
    http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(http)));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        // Construction du builder pour l'AuthenticationManager
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Retourne un AuthenticationManager configuré
        return authenticationManagerBuilder.build();
    }
}
