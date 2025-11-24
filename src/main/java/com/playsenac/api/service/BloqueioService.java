package com.playsenac.api.service;

import java.util.List;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.dto.BloqueioDTOId;

public interface BloqueioService {

    List<BloqueioDTOId> findAll();

    BloqueioDTOId findById(Integer id);

    BloqueioDTO addNew(BloqueioDTO dto);
    
    BloqueioDTO update(Integer id, BloqueioDTO dto);
    
    void delete(Integer id);
}
