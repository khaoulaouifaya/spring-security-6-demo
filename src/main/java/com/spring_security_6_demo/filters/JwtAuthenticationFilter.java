package com.spring_security_6_demo.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("inside attemptAuthentication method");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication result = authenticationManager.authenticate(authenticationToken);
        System.out.println("Authentication successful for user: " + username);
        return result;

         }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("inside successfulAuthentication method");
        System.out.println("Authentication successful");

        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("mySecret123");

        String jwtAccesToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // Expiration dans 5 minutes
                .withIssuer(request.getRequestURI().toUpperCase()) // L'émetteur du jeton
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())) // Récupère les rôles de l'utilisateur
                .sign(algorithm);
        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // Expiration dans 5 minutes
                .withIssuer(request.getRequestURI().toUpperCase()) // L'émetteur du jeton
                .sign(algorithm);
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access-token ", jwtAccesToken);
        tokens.put("refresh-token ", jwtRefreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        response.setContentType("application/json");
        // Définit l'en-tête Authorization avec le token
      //response.setStatus(HttpServletResponse.SC_OK);

    }
}
