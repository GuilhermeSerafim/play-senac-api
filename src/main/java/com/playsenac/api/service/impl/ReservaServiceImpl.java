package com.playsenac.api.service.impl;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuadraRepository quadraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaDTO:: fromEntity)
                .toList();
    }

    @Override
    public ReservaDTO findById(Integer id) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada para o ID: " + id));
        return ReservaDTO.fromEntity(entity);
    }

    @Override
    public ReservaDTO addNew(ReservaDTO dto) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o ID: " + dto.getIdUsuario()));

        QuadraEntity quadraEntity = quadraRepository.findById(dto.getIdQuadra())
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada para o ID: " + dto.getIdQuadra()));

        ReservaEntity entity = new ReservaEntity();
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setStatus("ATIVA");
        entity.setUsuario(usuarioEntity);
        entity.setQuadra(quadraEntity);
        entity.setQtdConvidados(dto.getQtdConvidados());
        ReservaEntity savedEntity = reservaRepository.save(entity);
        return ReservaDTO.fromEntity(savedEntity);
    }

    @Override
    public ReservaDTO updateHorario(Integer id, LocalDateTime novaDataHoraInicio, LocalDateTime novaDataHoraFim) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
