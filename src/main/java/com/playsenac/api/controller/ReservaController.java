package com.playsenac.api.controller;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.dto.ReservaDTOId;
import com.playsenac.api.service.ReservaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<ReservaDTOId>> findAll() {
        try {
            List<ReservaDTOId> reservas = service.findAll();
            return ResponseEntity.ok(reservas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ReservaDTOId>> minhasReservas() {
        List<ReservaDTOId> lista = service.findMinhasReservas();
        
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTOId> findById(@PathVariable Integer id) {
        try {
            ReservaDTOId reserva = service.findById(id);
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addNew(@Valid @RequestBody ReservaDTO dto) {
    	try {
            ReservaDTO novaReserva = service.addNew(dto);
            URI location = URI.create("/reservas/" + novaReserva.getDataHoraInicio());
            return ResponseEntity.created(location).body(novaReserva);
    	} catch (ResponseStatusException err) {
    		return ResponseEntity
                    .status(err.getStatusCode())
                    .body(err.getReason());
    	}
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid ReservaDTO dto) {
    	try {
    	   	ReservaDTO upDTO = service.update(id, dto);
        	return ResponseEntity.ok(upDTO);
		} catch (EntityNotFoundException err) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.getMessage());
		} catch (ResponseStatusException err) {
    		return ResponseEntity
                    .status(err.getStatusCode())
                    .body(err.getReason());
		}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.noContent().build();
    }

}
