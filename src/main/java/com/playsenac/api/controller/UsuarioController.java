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
    public ResponseEntity<?> addNew(@RequestBody @Valid UsuarioDTO dto) {
    	try {
            UsuarioDTO u = service.addNew(dto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{nome}")
                    .buildAndExpand(u.getNome())
                    .toUri();
            return ResponseEntity.created(location).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> update(@RequestBody @Valid UsuarioDTO dto) {
    	try {
            UsuarioDTO atualizado = service.update(dto);
            return ResponseEntity.ok(atualizado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}