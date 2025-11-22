package com.playsenac.api.controller;

import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SecurityRequirement(name = "bearer-jwt")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired
    private UsuarioService service;

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> findByEmail(@RequestParam String email) {
        UsuarioDTO usuario = service.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> addNew(@RequestBody UsuarioDTO dto) {

        UsuarioDTO u = service.addNew(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{nome}")
                .buildAndExpand(u.getNome())
                .toUri();
        return ResponseEntity.created(location).build();


    }
}
