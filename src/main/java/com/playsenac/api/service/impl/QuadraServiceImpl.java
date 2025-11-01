package com.playsenac.api.service.impl;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.service.QuadraService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuadraServiceImpl implements QuadraService {

    @Autowired
    private QuadraRepository quadraRepository;

    @Override
    public List<QuadraDTO> findAll() {
        List<QuadraEntity> entities = quadraRepository.findAll();
        List<QuadraDTO> resultado = new ArrayList<>();
        for (QuadraEntity entity : entities){
            QuadraDTO dto = new QuadraDTO(entity.getNome(),
                    entity.getStatus(),
                    entity.getDiaSemana(),
                    entity.getHorarioAbertura(),
                    entity.getHorarioFechamento(),
                    entity.getLimiteJogadores(),
                    entity.isInterna()
            );
            resultado.add(dto);
        }
        return resultado;
    }

    @Override
    public QuadraDTO findById(Integer id) {
        Optional<QuadraEntity> optQuadraEntity = quadraRepository.findById(id);
        if (optQuadraEntity.isEmpty()){
            throw new EntityNotFoundException("Quadra não encontrada para o ID: " + id);
        }
        QuadraEntity entity = optQuadraEntity.get();

        QuadraDTO dto = new QuadraDTO(
                entity.getNome(),
                entity.getStatus(),
                entity.getDiaSemana(),
                entity.getHorarioAbertura(),
                entity.getHorarioFechamento(),
                entity.getLimiteJogadores(),
                entity.isInterna()
        );
        return dto;
    }

    @Override
    public QuadraDTO addNew(QuadraDTO dto) {
        QuadraEntity entity = new QuadraEntity();
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
        entity.setDiaSemana(dto.getDiaSemana());
        entity.setHorarioAbertura(dto.getHorarioAbertura());
        entity.setHorarioFechamento(dto.getHorarioFechamento());
        entity.setLimiteJogadores(dto.getLimiteJogadores());
        entity.setInterna(dto.isInterna());
        entity = quadraRepository.save(entity);
        QuadraDTO resultado = new QuadraDTO(
                entity.getNome(),
                entity.getStatus(),
                entity.getDiaSemana(),
                entity.getHorarioAbertura(),
                entity.getHorarioFechamento(),
                entity.getLimiteJogadores(),
                entity.isInterna()
        );
        return resultado;
    }

    @Override
    public QuadraDTO update(Integer id, QuadraDTO dto) {
        QuadraEntity entidade = quadraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + id));

        entidade.setNome(dto.getNome());
        entidade.setStatus(dto.getStatus());
        entidade.setDiaSemana(dto.getDiaSemana());
        entidade.setHorarioAbertura(dto.getHorarioAbertura());
        entidade.setHorarioFechamento(dto.getHorarioFechamento());
        entidade.setLimiteJogadores(dto.getLimiteJogadores());
        entidade.setInterna(dto.isInterna());

        QuadraEntity entidadeAtualizada = quadraRepository.save(entidade);

        return new QuadraDTO(
                entidadeAtualizada.getNome(),
                entidadeAtualizada.getStatus(),
                entidadeAtualizada.getDiaSemana(),
                entidadeAtualizada.getHorarioAbertura(),
                entidadeAtualizada.getHorarioFechamento(),
                entidadeAtualizada.getLimiteJogadores(),
                entidadeAtualizada.isInterna()
        );
    }
    @Override
    public void delete(Integer id) {

    }
}
