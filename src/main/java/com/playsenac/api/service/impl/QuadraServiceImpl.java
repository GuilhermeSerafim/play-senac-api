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

    // Converter DTO para Entidade (Salvar no Banco)
    public QuadraEntity toEntity(QuadraDTO dto) {
        QuadraEntity entity = new QuadraEntity();
        entity.setNome(dto.getNome());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        
        // Novos campos mapeados:
        entity.setImagemUrl(dto.getImagemUrl());
        entity.setBloqueada(dto.isBloqueada());
        
        return entity;
    }

    // Converter Entidade para DTO (Enviar para o Front/JSON)
    public QuadraDTO toDto(QuadraEntity entity) {
        QuadraDTO dto = new QuadraDTO();

        dto.setNome(entity.getNome());
        dto.setLimiteJogadores(entity.getLimiteJogadores());
        
        // Novos campos mapeados:
        dto.setImagemUrl(entity.getImagemUrl());
        dto.setBloqueada(entity.isBloqueada());

        List<Integer> dias = new ArrayList<>();

        // Lógica para extrair dias e horários das disponibilidades
        if (entity.getDisponibilidades() != null && !entity.getDisponibilidades().isEmpty()) {
            dias = entity.getDisponibilidades().stream()
                    .map(DisponibilidadeEntity::getDia)
                    .toList();
            
            // Pega o horário da primeira disponibilidade para exibir no DTO pai
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

        // Salva as disponibilidades (dias e horários)
        if (dto.getDiasSemana() != null) {
            for (Integer dia : dto.getDiasSemana()) {
                DisponibilidadeEntity dispo = new DisponibilidadeEntity();
                dispo.setHorarioAbertura(dto.getHorarioAbertura());
                dispo.setHorarioFechamento(dto.getHorarioFechamento());
                dispo.setDia(dia);
                dispo.setQuadra(entity); // Vincula à quadra criada

                disponibilidadeRepository.save(dispo);
            }
        }
        
        // Retorna o DTO montado com os dados salvos
        QuadraDTO quadraDto = toDto(entity);
        // Preenche manualmente o que pode não ter vindo do toDto (se a lista da entity estiver vazia na memória)
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

        // Atualiza os dados
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