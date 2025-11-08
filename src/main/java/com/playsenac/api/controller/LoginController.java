package com.playsenac.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.playsenac.api.security.UsuarioSistema;
import com.playsenac.api.service.JwtService;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService service;

    @PostMapping("/login")
    public ResponseEntity<RespostaLogin> fazerLogin(@RequestBody Credencial credencial) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credencial.username(),
                        credencial.senha()));
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UsuarioSistema usuario = (UsuarioSistema) auth.getPrincipal();
        String jwt = service.gerarTokenJwt(usuario);
        return ResponseEntity.ok().body(new RespostaLogin(usuario.getEmail(), jwt));
    }

    public record Credencial(String username, String senha) {
    }

    public record RespostaLogin(String nome, String token) {
    }
}
