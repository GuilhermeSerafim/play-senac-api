package com.playsenac.api.controller;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reservas")
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

}
