package com.playsenac.api.controller;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.service.QuadraService;
import com.playsenac.api.service.impl.QuadraServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/quadras")
public class QuadraController {

    @Autowired
    private QuadraService service;

    @GetMapping
    public List<QuadraDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuadraDTO> findById(@PathVariable Integer id) {
        try {
            QuadraDTO quadra = service.findById(id);
            return ResponseEntity.ok(quadra);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<QuadraDTO> addNew(@RequestBody @Valid QuadraDTO quadraDTO) {
        QuadraDTO quadraDto = service.addNew(quadraDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(quadraDto.getNome()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuadraDTO> update(@PathVariable Integer id, @RequestBody @Valid QuadraDTO quadraDTO) {
        QuadraDTO atualizado = service.update(id, quadraDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
