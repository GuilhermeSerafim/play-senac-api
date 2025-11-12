package com.playsenac.api.service;

import java.util.List;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.dto.QuadraDTO;

public interface BloqueioService {

    List<BloqueioDTO> findAll();

    BloqueioDTO findById(Integer id);

    BloqueioDTO addNew(BloqueioDTO dto);
}
