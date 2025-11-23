package com.playsenac.api.controller;

import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> findMyDetails() {
        UsuarioDTO usuario = service.findMyDetails();
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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO atualizado = service.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}