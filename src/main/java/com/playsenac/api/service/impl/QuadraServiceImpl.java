package com.playsenac.api.service.impl;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.entities.DisponibilidadeEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.repository.DisponibilidadeRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.service.QuadraService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuadraServiceImpl implements QuadraService {

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public QuadraEntity toEntity(QuadraDTO dto) {
        QuadraEntity entity = new QuadraEntity();
        entity.setNome(dto.getNome());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        entity.setImagemUrl(dto.getImagemUrl());
        entity.setBloqueada(dto.isBloqueada());
        
        return entity;
    }

    public QuadraDTO toDto(QuadraEntity entity) {
        QuadraDTO dto = new QuadraDTO();

        dto.setNome(entity.getNome());
        dto.setLimiteJogadores(entity.getLimiteJogadores());
        
        dto.setImagemUrl(entity.getImagemUrl());
        dto.setBloqueada(entity.isBloqueada());

        List<Integer> dias = new ArrayList<>();

        if (entity.getDisponibilidades() != null && !entity.getDisponibilidades().isEmpty()) {
            dias = entity.getDisponibilidades().stream()
                    .map(DisponibilidadeEntity::getDia)
                    .toList();
            
            DisponibilidadeEntity primeiraDispo = entity.getDisponibilidades().get(0);
            dto.setHorarioAbertura(primeiraDispo.getHorarioAbertura());
            dto.setHorarioFechamento(primeiraDispo.getHorarioFechamento());
        }

        dto.setDiasSemana(dias);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuadraDTO> findAll() {
        return quadraRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QuadraDTO findById(Integer id) {
        QuadraEntity entity = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));
        return toDto(entity);
    }

    @Override
    @Transactional
    public QuadraDTO addNew(QuadraDTO dto) {
        QuadraEntity entity = toEntity(dto);
        entity = quadraRepository.save(entity);

        if (dto.getDiasSemana() != null) {
            for (Integer dia : dto.getDiasSemana()) {
                DisponibilidadeEntity dispo = new DisponibilidadeEntity();
                dispo.setHorarioAbertura(dto.getHorarioAbertura());
                dispo.setHorarioFechamento(dto.getHorarioFechamento());
                dispo.setDia(dia);
                dispo.setQuadra(entity);

                disponibilidadeRepository.save(dispo);
            }
        }
        
        QuadraDTO quadraDto = toDto(entity);
        quadraDto.setDiasSemana(dto.getDiasSemana());
        quadraDto.setHorarioAbertura(dto.getHorarioAbertura());
        quadraDto.setHorarioFechamento(dto.getHorarioFechamento());

        return quadraDto;
    }

    @Override
    @Transactional
    public QuadraDTO update(Integer id, QuadraDTO dto) {
        QuadraEntity entity = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));

        entity.setNome(dto.getNome());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        entity.setImagemUrl(dto.getImagemUrl());
        entity.setBloqueada(dto.isBloqueada());

        entity = quadraRepository.save(entity);

        return toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        QuadraEntity entidade = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));
        quadraRepository.delete(entidade);
    }
}