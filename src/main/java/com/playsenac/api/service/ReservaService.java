package com.playsenac.api.service;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.dto.ReservaDTOId;

import java.util.List;

public interface ReservaService {
    List<ReservaDTOId> findAll();

    ReservaDTOId findById(Integer id);

    ReservaDTO addNew(ReservaDTO dto);

    void delete (Integer id);

	ReservaDTO update(Integer id, ReservaDTO dto);
}
