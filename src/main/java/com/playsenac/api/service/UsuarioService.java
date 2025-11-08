package com.playsenac.api.service;


import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.entities.UsuarioEntity;

import java.util.Optional;

public interface UsuarioService {
    UsuarioDTO findById(Integer id);
    UsuarioDTO addNew(UsuarioDTO dto);
}
