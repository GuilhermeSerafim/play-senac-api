package com.playsenac.api.service.impl;

import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.dto.ReservaDTOId;
import com.playsenac.api.entities.ConvidadoEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.ConvidadoRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import com.playsenac.api.security.UsuarioSistema;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuadraRepository quadraRepository;
    
    @Autowired
    private ConvidadoRepository convidadoRepository;

    public ReservaDTO toDto(ReservaEntity entity) {
    	ReservaDTO dto = new ReservaDTO();
    	dto.setDataHoraInicio(entity.getDataHoraInicio());
    	dto.setDataHoraFim(entity.getDataHoraFim());
    	dto.setIdUsuario(entity.getUsuario().getId_usuario());
    	dto.setIdQuadra(entity.getQuadra().getId_quadra());
    	dto.setConvidados(entity.getConvidados());
    	return dto;
    }
    
    public ReservaDTOId toDtoId(ReservaEntity entity) {
    	ReservaDTOId dto = new ReservaDTOId();
    	dto.setId(entity.getId_reserva());
    	dto.setDataHoraInicio(entity.getDataHoraInicio());
    	dto.setDataHoraFim(entity.getDataHoraFim());
    	dto.setIdUsuario(entity.getUsuario().getId_usuario());
    	dto.setIdQuadra(entity.getQuadra().getId_quadra());
    	dto.setConvidados(entity.getConvidados());
    	return dto;
    }
    
    public ReservaEntity toEntity(ReservaDTO dto) {
    	UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
    	QuadraEntity quadra = quadraRepository.findById(dto.getIdQuadra())
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada com ID: " + dto.getIdQuadra()));
    	ReservaEntity entity = new ReservaEntity();
    	if(usuario != null && quadra != null) {
        	entity.setDataHoraInicio(dto.getDataHoraInicio());
        	entity.setDataHoraFim(dto.getDataHoraFim());
        	entity.setQuadra(quadra);
        	entity.setUsuario(usuario);
    	}
    	return entity;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTOId> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toDtoId)
                .toList();
    }

    @Override
    public ReservaDTOId findById(Integer id) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada para o ID: " + id));
        return toDtoId(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTOId> findMinhasReservas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        UsuarioSistema usuarioLogado = (UsuarioSistema) auth.getPrincipal();
        Integer idLogado = usuarioLogado.getId_usuario();

        List<ReservaEntity> reservas = reservaRepository.findPorUsuario(idLogado);

        return reservas.stream()
                .map(this::toDtoId)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public ReservaDTO addNew(ReservaDTO dto) {
        ReservaEntity entity = toEntity(dto);
        
        entity = reservaRepository.save(entity);
        
        if(dto.getConvidados() != null && !dto.getConvidados().isEmpty()) {
            for(ConvidadoEntity convidado : dto.getConvidados()) {
                
                convidado.setReserva(entity); 
                convidadoRepository.save(convidado);
            }
            
            entity.setConvidados(dto.getConvidados());
        }
        
        return toDto(entity);
    }

    @Override
    @Transactional
    public ReservaDTO update(Integer id, ReservaDTO dto) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada para o ID: " + id));

        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        if (!(entity.getUsuario().getId_usuario() == (dto.getIdUsuario()))) {
            UsuarioEntity novoUsuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getIdUsuario()));
            entity.setUsuario(novoUsuario);
        }

        if (!entity.getQuadra().getId_quadra().equals(dto.getIdQuadra())) {
            QuadraEntity novaQuadra = quadraRepository.findById(dto.getIdQuadra())
                    .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada com ID: " + dto.getIdQuadra()));
            entity.setQuadra(novaQuadra);
        }

        if (dto.getConvidados() != null) {
            entity.getConvidados().clear();

            for (ConvidadoEntity convidado : dto.getConvidados()) {
                convidado.setReserva(entity); 
                entity.getConvidados().add(convidado);
            }
        }
        entity = reservaRepository.save(entity);

        return toDto(entity);
    }

    @Override
    public void delete(Integer id) {
    	ReservaEntity entity = reservaRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("reserva não encontrada para o id " + id));
    	reservaRepository.delete(entity);
    }
}
