package com.playsenac.api.controller;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.service.ReservaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reservas")
@SecurityRequirement(name = "bearer-jwt")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> findAll() {
        try {
            List<ReservaDTO> reservas = service.findAll();
            return ResponseEntity.ok(reservas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> findById(@PathVariable Integer id) {
        try {
            ReservaDTO reserva = service.findById(id);
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> addNew(@Valid @RequestBody ReservaDTO dto) {
        ReservaDTO novaReserva = service.addNew(dto);
        URI location = URI.create("/reservas/" + novaReserva.getDataHoraInicio());
        return ResponseEntity.created(location).body(novaReserva);
    }

}
