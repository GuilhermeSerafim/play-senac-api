package com.playsenac.api.service.impl;

import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o ID: " + id));
        return UsuarioDTO.fromEntity(entity);

    }

    public UsuarioEntity toEntity(UsuarioDTO dto) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setTelefone(dto.getTelefone());
        entity.setFkRole(dto.getFk_role());
        return entity;
    }

//    public UsuarioDTO toDTO(UsuarioEntity entity) {
//        UsuarioDTO dto = new UsuarioDTO();
//        dto.setNome(entity.getNome());
//        dto.setEmail(entity.getEmail());
//        dto.setSenha(entity.getSenha());
//        return dto;
//    }

    @Override
    @Transactional
    public UsuarioDTO addNew(UsuarioDTO dto) {
        UsuarioEntity ue = toEntity(dto);
        usuarioRepository.save(ue);
        return dto;
    }
}
