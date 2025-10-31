package com.playsenac.api.service.impl;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.service.QuadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    return null;
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
    public QuadraDTO atualizarQuadra(Integer id, QuadraDTO dto) {
        return null;
    }

    @Override
    public void deletarQuadra(Integer id) {

    }
}
