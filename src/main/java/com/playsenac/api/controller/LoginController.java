package com.playsenac.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.playsenac.api.security.UsuarioSistema;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

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
        return ResponseEntity.ok().body(new RespostaLogin(usuario.getEmail(), "token1234"));
    }

    public record Credencial(String username, String senha) {
    }

    public record RespostaLogin(String nome, String token) {
    }
}
