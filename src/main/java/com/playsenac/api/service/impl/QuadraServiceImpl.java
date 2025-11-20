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
    	entity.setStatus(dto.isStatus());
    	entity.setLimiteJogadores(dto.getLimiteJogadores());
    	entity.setInterna(dto.isInterna());
    	return entity;
    }
    
    public QuadraDTO toDto(QuadraEntity entity) {
    	
    	List<Integer> dias = entity.getDisponibilidades().stream()
                .map(dispo -> dispo.getDia())
                .toList();
    	QuadraDTO dto = new QuadraDTO(
    			entity.getId(), 
    			entity.getNome(), 
    			entity.isStatus(), 
    			entity.getLimiteJogadores(), 
    			entity.isInterna()
    			);
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
        for(Integer dia : dto.getDiasSemana()) {
        	DisponibilidadeEntity dispoEntity = new DisponibilidadeEntity();
        	dispoEntity.setHorarioAbertura(dto.getHorarioAbertura());
        	dispoEntity.setHorarioFechamento(dto.getHorarioFechamento());
        	dispoEntity.setDia(dia);
        	dispoEntity.setQuadra(entity);
        	disponibilidadeRepository.save(dispoEntity);
        }
        return toDto(entity);
    }

    @Override
    @Transactional
    public QuadraDTO update(Integer id, QuadraDTO dto) {
        QuadraEntity entity = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));
        
        entity.setNome(dto.getNome());
        entity.setStatus(dto.isStatus());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        entity.setInterna(dto.isInterna());

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