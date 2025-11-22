package com.playsenac.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playsenac.api.dto.BloqueioDTO;
import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.BloqueioRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.BloqueioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BloqueioServiceImpl implements BloqueioService{

    @Autowired
    private BloqueioRepository repository;

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public BloqueioDTO findById(Integer id){
        BloqueioEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bloqueio não encontrado: " + id));

        return BloqueioDTO.fromEntity(entity);
    }

    @Override
    public List<BloqueioDTO> findAll() {
        return repository.findAll().stream().map(BloqueioDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BloqueioDTO addNew(BloqueioDTO dto) {
        QuadraEntity quadra = quadraRepository.findById(dto.getIdQuadra()).orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));
        UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        List<ReservaEntity> reservasConflitantes = reservaRepository.findByQuadraAndDataHoraInicioBeforeAndDataHoraFimAfter(
            quadra, 
            dto.getDataHoraFim(), 
            dto.getDataHoraInicio()
        );

        if (!reservasConflitantes.isEmpty()) {
            throw new  IllegalStateException("Não é possivel bloquear Horario. Há " + reservasConflitantes.size() +  "reservas ativas no periodo");
        } else {

            BloqueioEntity entity = new BloqueioEntity();
            entity.setDataHoraInicio(dto.getDataHoraInicio());
            entity.setDataHoraFim(dto.getDataHoraFim());
            entity.setMotivo(dto.getMotivo());
            entity.setQuadraBloqueada(quadra);
            entity.setUsuarioBloqueador(usuario);
            
            BloqueioEntity entidadeSalva = repository.save(entity);
    
            quadraRepository.save(quadra);
            
            return BloqueioDTO.fromEntity(entidadeSalva);
        }

    }

   
}
