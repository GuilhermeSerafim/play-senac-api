package com.playsenac.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.repository.BloqueioRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.service.BloqueioService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BloqueioServiceImpl implements BloqueioService{

    @Autowired
    private BloqueioRepository repository;

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    

    @Override
    public BloqueioDTO findById(Integer id){
        BloqueioEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado: " + id));

        return BloqueioDTO.fromEntity(entity);
    }

    @Override
    public List<BloqueioDTO> findAll() {
        return repository.findAll().stream().map(BloqueioDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public BloqueioDTO addNew(BloqueioDTO dto) {
        QuadraEntity quadra = quadraRepository.findById(dto.getIdQuadraBloqueada()).orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));

        List<ReservaEntity> reservasConflitantes = reservaRepository.findByQuadraAndStatusAndDataHoraInicioBeforeAndDataHoraFimAfter(
            quadra, 
            "Ativa", 
            dto.getDataHoraFim(), 
            dto.getDataHoraInicio()
        );

        if (!reservasConflitantes.isEmpty()) {
            throw new  IllegalStateException("Não é possivel bloquear Horario. Há " + reservasConflitantes.size() +  "reservas ativas no periodo");
        } else {

            BloqueioEntity entity = new BloqueioEntity();
            entity.setDataHoraInicio(dto.getDataHoraInicio());
            entity.setDataHoraFim(dto.getDataHoraFim());
            entity.setQuadraBloqueada(quadra);
            
            BloqueioEntity entidadeSalva = repository.save(entity);
    
            
            return BloqueioDTO.fromEntity(entidadeSalva);
        }

    }

   
}
