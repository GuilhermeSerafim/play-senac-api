package com.playsenac.api.service;

import com.playsenac.api.dto.QuadraDTO;
import com.playsenac.api.dto.QuadraDTOId;

import java.util.List;

public interface QuadraService {
    List<QuadraDTOId> findAll();

    QuadraDTOId findById(Integer id);

    QuadraDTO addNew(QuadraDTO dto);

   QuadraDTO update(Integer id, QuadraDTO dto);

    void delete(Integer id);
}
