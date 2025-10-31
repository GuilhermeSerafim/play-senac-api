package com.playsenac.api.service;

import com.playsenac.api.dto.QuadraDTO;


import java.util.List;

public interface QuadraService {
    List<QuadraDTO> findAll();
    QuadraDTO findById(Integer id);
    QuadraDTO addNew(QuadraDTO dto);
    QuadraDTO atualizarQuadra(Integer id, QuadraDTO dto);
    void deletarQuadra(Integer id);
}
