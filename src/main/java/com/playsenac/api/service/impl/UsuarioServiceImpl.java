package com.playsenac.api.service.impl;

import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;
    


    public UsuarioEntity toEntity(UsuarioDTO dto) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setTelefone(dto.getTelefone());
        entity.setFkRole(1);
        return entity;
    }

    @Override
    public UsuarioDTO findByEmail(String email) {
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o email: " + email));
        return UsuarioDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public UsuarioDTO addNew(UsuarioDTO dto) {
        UsuarioEntity ue = toEntity(dto);
        String senhaCriptografada = encoder.encode(ue.getSenha());
        ue.setSenha(senhaCriptografada);
        usuarioRepository.save(ue);
        return dto;
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(encoder.encode(dto.getSenha()));
        entity.setTelefone(dto.getTelefone());

        UsuarioEntity atualizado = usuarioRepository.save(entity);
        return UsuarioDTO.fromEntity(atualizado);
    }

    @Override
    public void delete(Integer id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(entity);

    }
}
