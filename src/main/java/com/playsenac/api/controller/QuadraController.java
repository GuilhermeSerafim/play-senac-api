package com.playsenac.api.controller;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.service.QuadraService;
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
public List<QuadraDTO> findAll(){
    return service.findAll();
}

@PostMapping
public ResponseEntity<QuadraDTO> criarQuadra(@RequestBody @Valid QuadraDTO quadraDTO){
    QuadraDTO quadraDto = service.addNew(quadraDTO);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(quadraDto.getNome())
            .toUri();
    return ResponseEntity.created(location).body(quadraDto);
}

}
