package com.playsenac.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playsenac.api.dto.BloqueioDTO;
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

    @Override
    public BloqueioDTO findById(Integer id){
        BloqueioEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado: " + id));

        return toDto(entity);
    }

    @Override
    public List<BloqueioDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BloqueioDTO toDto(BloqueioEntity entity) {
        BloqueioDTO dto = new BloqueioDTO();
        dto.setDataHoraInicio(entity.getDataHoraInicio());
        dto.setDataHoraFim(entity.getDataHoraFim());
        dto.setMotivo(entity.getMotivo());
        
        if (entity.getQuadra() != null) {
            dto.setIdQuadra(entity.getQuadra().getId_quadra());
        }
        return dto;
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

        BloqueioEntity entity = new BloqueioEntity();
        entity.setQuadra(quadra);
        entity.setUsuarioBloqueador(usuario);
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setMotivo(dto.getMotivo());

        entity = repository.save(entity);

        return toDto(entity);
    }
}