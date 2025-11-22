package com.playsenac.api.service;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.dto.ReservaDTOId;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaService {
    List<ReservaDTOId> findAll();

    ReservaDTOId findById(Integer id);

    ReservaDTO addNew(ReservaDTO dto);

    ReservaDTO update(Integer id, LocalDateTime novaDataHoraInicio, LocalDateTime novaDataHoraFim);

    void delete (Integer id);
}
