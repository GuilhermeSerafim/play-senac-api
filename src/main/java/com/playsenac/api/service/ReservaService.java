package com.playsenac.api.service;

import com.playsenac.api.dto.ReservaDTO;


import java.time.LocalDateTime;
import java.util.List;

public interface ReservaService {
    List<ReservaDTO> findAll();

    ReservaDTO findById(Integer id);

    ReservaDTO addNew(ReservaDTO dto);

    ReservaDTO updateHorario(Integer id, LocalDateTime novaDataHoraInicio, LocalDateTime novaDataHoraFim);

    void delete (Integer id);
}
