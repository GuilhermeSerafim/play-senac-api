package com.playsenac.api.service;


import com.playsenac.api.dto.UsuarioDTO;

public interface UsuarioService  {
    UsuarioDTO findByEmail(String email);
    UsuarioDTO addNew(UsuarioDTO dto);
    UsuarioDTO update(Integer id, UsuarioDTO dto);
    void delete(Integer id);

}
