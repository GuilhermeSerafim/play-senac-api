package com.playsenac.api.service.impl;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

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
        return null;
    }

    @Override
    public ReservaDTO addNew(ReservaDTO dto) {
        return null;
    }

    @Override
    public ReservaDTO updateHorario(Integer id, LocalDateTime novaDataHoraInicio, LocalDateTime novaDataHoraFim) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
