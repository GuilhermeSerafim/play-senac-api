package com.playsenac.api.service.impl;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.service.QuadraService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class QuadraServiceImpl implements QuadraService {

    @Autowired
    private QuadraRepository quadraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<QuadraDTO> findAll() {
        return quadraRepository.findAll()
                .stream()
                .map(QuadraDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QuadraDTO findById(Integer id) {
        QuadraEntity entity = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));
        return QuadraDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public QuadraDTO addNew(QuadraDTO dto) {
        QuadraEntity entity = dto.toEntity();
        entity = quadraRepository.save(entity);
        return QuadraDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public QuadraDTO update(Integer id, QuadraDTO dto) {
        QuadraEntity entity = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));


        entity.setNome(dto.getNome());
        entity.setStatus(dto.isStatus());
        entity.setHorarioAbertura(dto.getHorarioAbertura());
        entity.setHorarioFechamento(dto.getHorarioFechamento());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        entity.setInterna(dto.isInterna());

        entity = quadraRepository.save(entity);

        return QuadraDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        QuadraEntity entidade = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));
        quadraRepository.delete(entidade);
    }

}