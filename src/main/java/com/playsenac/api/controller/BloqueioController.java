package com.playsenac.api.controller;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.dto.BloqueioDTOId;
import com.playsenac.api.service.BloqueioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bloqueios")
@SecurityRequirement(name = "bearer-jwt")
public class BloqueioController {

    @Autowired
    private BloqueioService service;

    @GetMapping
    public List<BloqueioDTOId> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueioDTOId> findById(@PathVariable Integer id) {
        try {
            BloqueioDTOId bloqueio = service.findById(id);
            return ResponseEntity.ok(bloqueio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BloqueioDTO> addNew(@RequestBody @Valid BloqueioDTO bloqueioDTO) {
        BloqueioDTO dtoSalvo = service.addNew(bloqueioDTO);

        URI location = URI.create("/bloqueios/" + dtoSalvo.getIdQuadra());

        return ResponseEntity.created(location).body(dtoSalvo);
    }

}
