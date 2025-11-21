package com.playsenac.api.service;

import java.util.List;

import com.playsenac.api.dto.BloqueioDTO;

public interface BloqueioService {

    List<BloqueioDTO> findAll();

    BloqueioDTO findById(Integer id);

    BloqueioDTO addNew(BloqueioDTO dto);
}
