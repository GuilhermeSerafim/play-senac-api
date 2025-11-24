package com.playsenac.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.dto.BloqueioDTOId;
import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.BloqueioRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.BloqueioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BloqueioServiceImpl implements BloqueioService {

    @Autowired
    private BloqueioRepository repository;

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private BloqueioDTOId toDtoId(BloqueioEntity entity) {
        BloqueioDTOId dto = new BloqueioDTOId();
        dto.setId(entity.getId_bloqueio());
        dto.setDataHoraInicio(entity.getDataHoraInicio());
        dto.setDataHoraFim(entity.getDataHoraFim());
        dto.setMotivo(entity.getMotivo());
        
        if (entity.getQuadra() != null) {
            dto.setIdQuadra(entity.getQuadra().getId_quadra());
        }
        if (entity.getUsuarioBloqueador() != null) {
            dto.setIdUsuario(entity.getUsuarioBloqueador().getId_usuario());
        }
        return dto;
    }
    
    private BloqueioDTO toDto(BloqueioEntity entity) {
        BloqueioDTO dto = new BloqueioDTO();
        dto.setDataHoraInicio(entity.getDataHoraInicio());
        dto.setDataHoraFim(entity.getDataHoraFim());
        dto.setMotivo(entity.getMotivo());
        
        if (entity.getQuadra() != null) {
            dto.setIdQuadra(entity.getQuadra().getId_quadra());
        }
        if (entity.getUsuarioBloqueador() != null) {
            dto.setIdUsuario(entity.getUsuarioBloqueador().getId_usuario());
        }
        return dto;
    }
    
    
    @Override
    public BloqueioDTOId findById(Integer id){
        BloqueioEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado: " + id));

        return toDtoId(entity);
    }

    @Override
    public List<BloqueioDTOId> findAll() {
        return repository.findAll().stream()
                .map(this::toDtoId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BloqueioDTO addNew(BloqueioDTO dto) {
        QuadraEntity quadra = quadraRepository.findById(dto.getIdQuadra())
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));
        
        UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        List<ReservaEntity> reservasConflitantes = reservaRepository.findConflitos(
                dto.getIdQuadra(),
                dto.getDataHoraInicio(),
                dto.getDataHoraFim()
        );

        if (!reservasConflitantes.isEmpty()) {
            reservaRepository.deleteAll(reservasConflitantes);
        }

        quadra.setBloqueada(true);
        quadraRepository.save(quadra);
        
        BloqueioEntity entity = new BloqueioEntity();
        entity.setQuadra(quadra);
        entity.setUsuarioBloqueador(usuario);
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setMotivo(dto.getMotivo());

        entity = repository.save(entity);

        return toDto(entity);
    }

    @Override
    @Transactional
    public BloqueioDTO update(Integer id, BloqueioDTO dto) {
        BloqueioEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado!"));
        
        QuadraEntity quadraAntiga = entity.getQuadra();

        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setMotivo(dto.getMotivo());
        
        if (dto.getIdUsuario() != null && !entity.getUsuarioBloqueador().getId_usuario().equals(dto.getIdUsuario())) {
            UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
            entity.setUsuarioBloqueador(usuario);
        }
        
        if (dto.getIdQuadra() != null && !quadraAntiga.getId_quadra().equals(dto.getIdQuadra())) {
            QuadraEntity novaQuadra = quadraRepository.findById(dto.getIdQuadra())
                    .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));
            
            novaQuadra.setBloqueada(true);
            quadraRepository.save(novaQuadra);
            
            entity.setQuadra(novaQuadra);

            long bloqueiosRestantes = repository.countBloqueiosNaQuadra(quadraAntiga, id);
            
            if (bloqueiosRestantes == 0) {
                quadraAntiga.setBloqueada(false);
                quadraRepository.save(quadraAntiga);
            }
        }
        
        List<ReservaEntity> reservasConflitantes = reservaRepository.findConflitos(
                entity.getQuadra().getId_quadra(),
                entity.getDataHoraInicio(),
                entity.getDataHoraFim()
        );
        
        if (!reservasConflitantes.isEmpty()) {
            reservaRepository.deleteAll(reservasConflitantes);
        }
        
        entity = repository.save(entity);
        
        return toDto(entity);
    }
    
    @Override
    @Transactional
    public void delete(Integer id) {
        BloqueioEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado para o ID: " + id));

        QuadraEntity quadra = entity.getQuadra();

        repository.delete(entity);
        repository.flush(); 

        long bloqueiosRestantes = repository.countByQuadra(quadra);

        if (bloqueiosRestantes == 0) {
            quadra.setBloqueada(false);
            quadraRepository.save(quadra);
        }
    }
    
    
}