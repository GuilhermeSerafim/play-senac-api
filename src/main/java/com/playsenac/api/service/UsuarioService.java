package com.playsenac.api.service;


import com.playsenac.api.dto.UsuarioDTO;

public interface UsuarioService  {
	UsuarioDTO findMyDetails();
    UsuarioDTO addNew(UsuarioDTO dto);
    UsuarioDTO update(Integer id, UsuarioDTO dto);
    void delete(Integer id);

}
