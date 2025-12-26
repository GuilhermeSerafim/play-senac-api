package com.playsenac.api.service.impl;

import com.playsenac.api.dto.UsuarioDTO;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.security.UsuarioSistema;
import com.playsenac.api.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        // User default
        entity.setFkRole(1);
        return entity;
    }
    
    @Override
    public UsuarioDTO findMyDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado. Faça login para acessar.");
        }
        Object principal = auth.getPrincipal();
        UsuarioSistema usuarioLogado;

        if (!(principal instanceof UsuarioSistema)) {
             throw new AccessDeniedException("Token inválido ou expirado. Renove a autenticação.");
        }
        
        usuarioLogado = (UsuarioSistema) principal;
        Integer idLogado = usuarioLogado.getId_usuario();

        UsuarioEntity entity = usuarioRepository.findById(idLogado)
            .orElseThrow(() -> new EntityNotFoundException("Usuário logado não encontrado no banco de dados."));

        return UsuarioDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public UsuarioDTO addNew(UsuarioDTO dto) {        
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O email já está cadastrado no sistema.");
        }
        
        UsuarioEntity ue = toEntity(dto);
        String senhaCriptografada = encoder.encode(ue.getSenha());
        ue.setSenha(senhaCriptografada);
        usuarioRepository.save(ue);
        return dto;
    }

    @Override
    public UsuarioDTO update(UsuarioDTO dto) {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();

         if (auth == null || !auth.isAuthenticated()) {
             throw new AccessDeniedException("Usuário não autenticado. Faça login para acessar.");
         }
         Object principal = auth.getPrincipal();
         UsuarioSistema usuarioLogado;

         if (!(principal instanceof UsuarioSistema)) {
              throw new AccessDeniedException("Token inválido ou expirado. Renove a autenticação.");
         }
         
         usuarioLogado = (UsuarioSistema) principal;
         Integer idLogado = usuarioLogado.getId_usuario();
        UsuarioEntity entity = usuarioRepository.findById(idLogado)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        Optional<UsuarioEntity> emailExistente = usuarioRepository.findByEmail(dto.getEmail());

        if (emailExistente.isPresent() && !emailExistente.get().getId_usuario().equals(idLogado)) {
            throw new IllegalArgumentException("O novo email já está sendo usado por outro usuário.");
        }
        
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(encoder.encode(dto.getSenha()));
        entity.setTelefone(dto.getTelefone());

        UsuarioEntity atualizado = usuarioRepository.save(entity);
        return UsuarioDTO.fromEntity(atualizado);
    }

    @Override
    public void delete() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado. Faça login para acessar.");
        }
        Object principal = auth.getPrincipal();
        UsuarioSistema usuarioLogado;

        if (!(principal instanceof UsuarioSistema)) {
             throw new AccessDeniedException("Token inválido ou expirado. Renove a autenticação.");
        }
        
        usuarioLogado = (UsuarioSistema) principal;
        Integer idLogado = usuarioLogado.getId_usuario();
        UsuarioEntity entity = usuarioRepository.findById(idLogado)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(entity);

    }
}
