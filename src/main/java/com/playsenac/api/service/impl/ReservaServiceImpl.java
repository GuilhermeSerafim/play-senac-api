package com.playsenac.api.service.impl;

import com.playsenac.api.dto.QuadraDTOId;
import com.playsenac.api.dto.ReservaDTO;
import com.playsenac.api.dto.ReservaDTOId;
import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.ConvidadoEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import com.playsenac.api.repository.BloqueioRepository;
import com.playsenac.api.repository.ConvidadoRepository;
import com.playsenac.api.repository.QuadraRepository;
import com.playsenac.api.repository.ReservaRepository;
import com.playsenac.api.repository.UsuarioRepository;
import com.playsenac.api.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
	
	@Autowired
	private BloqueioRepository bloqueioRepository;

	public ReservaDTO toDto(ReservaEntity entity) {
		ReservaDTO dto = new ReservaDTO();
		dto.setDataHoraInicio(entity.getDataHoraInicio());
		dto.setDataHoraFim(entity.getDataHoraFim());
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || !auth.isAuthenticated()) {
			throw new AccessDeniedException("Usuário não autenticado.");
		}

		UsuarioSistema usuarioLogado = (UsuarioSistema) auth.getPrincipal();
		Integer idLogado = usuarioLogado.getId_usuario();
		UsuarioEntity usuario = usuarioRepository.findById(idLogado)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		
		QuadraEntity quadra = quadraRepository.findById(dto.getIdQuadra())
				.orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada com ID: " + dto.getIdQuadra()));
		
		ReservaEntity entity = new ReservaEntity();
		
		if (usuario != null && quadra != null) {
			entity.setDataHoraInicio(dto.getDataHoraInicio());
			entity.setDataHoraFim(dto.getDataHoraFim());
			entity.setQuadra(quadra);
			entity.setUsuario(usuario);
			
			if (dto.getConvidados() != null) {
				entity.setConvidados(dto.getConvidados());
			}
		}
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservaDTOId> findAll() {
		return reservaRepository.findAll().stream().map(this::toDtoId).toList();
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

		return reservas.stream().map(this::toDtoId).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ReservaDTO addNew(ReservaDTO dto) {
		ReservaEntity entity = toEntity(dto);
		
		if (entity.getDataHoraInicio().isAfter(entity.getDataHoraFim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                   "A data final não pode ser anterior à data inicial.");
       }

       boolean existeBloqueio = bloqueioRepository.existeBloqueioNoIntervalo(
               entity.getQuadra().getId_quadra(),
               entity.getDataHoraInicio(),
               entity.getDataHoraFim()
       );

       if (existeBloqueio) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, 
                   "Não é possível reservar: A quadra está bloqueada neste período.");
       }
       
       boolean existeReserva = reservaRepository.existeReservaNoIntervalo(entity.getQuadra().getId_quadra(), entity.getDataHoraInicio(), entity.getDataHoraFim());

		if (existeReserva) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, 
					"Já existe uma reserva confirmada para este horário.");
		}
		
		if (entity.getConvidados() != null && !entity.getConvidados().isEmpty()) {
            for (ConvidadoEntity convidado : entity.getConvidados()) {
                convidado.setReserva(entity);
            }
        }
		
		entity = reservaRepository.save(entity);

		return toDto(entity);
	}

	@Override
    @Transactional
    public ReservaDTO update(Integer id, ReservaDTO dto) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada para o ID: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioSistema usuarioLogado = (UsuarioSistema) auth.getPrincipal();
        Integer idLogado = usuarioLogado.getId_usuario();

        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());

        if (!entity.getUsuario().getId_usuario().equals(idLogado)) {
            UsuarioEntity novoUsuario = usuarioRepository.findById(idLogado)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            entity.setUsuario(novoUsuario);
        }

        if (!entity.getQuadra().getId_quadra().equals(dto.getIdQuadra())) {
            QuadraEntity novaQuadra = quadraRepository.findById(dto.getIdQuadra())
                    .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada ID: " + dto.getIdQuadra()));
            entity.setQuadra(novaQuadra);
        }

        if (dto.getConvidados() != null) {
            entity.getConvidados().clear();

            for (ConvidadoEntity convidado : dto.getConvidados()) {
                convidado.setReserva(entity);
                entity.getConvidados().add(convidado);
            }
        }
        
        if (entity.getDataHoraInicio().isAfter(entity.getDataHoraFim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A data final não pode ser anterior à data inicial.");
        }

        boolean existeBloqueio = bloqueioRepository.existeBloqueioNoIntervalo(
                entity.getQuadra().getId_quadra(),
                entity.getDataHoraInicio(),
                entity.getDataHoraFim()
        );

        if (existeBloqueio) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível reservar: A quadra está bloqueada neste período.");
        }

        boolean existeReserva = reservaRepository.existeReservaNoIntervaloIgnorandoId(
                entity.getQuadra().getId_quadra(),
                entity.getDataHoraInicio(),
                entity.getDataHoraFim(),
                id
        );

        if (existeReserva) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe outra reserva confirmada para este horário.");
        }

        entity = reservaRepository.save(entity);

        return toDto(entity);
    }

	@Override
	@Transactional
	public void delete(Integer id) {
		ReservaEntity entity = reservaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada para o id " + id));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new AccessDeniedException("Usuário não autenticado.");
		}

		Object principal = authentication.getPrincipal();
		UsuarioSistema usuarioLogado;

		try {
			usuarioLogado = (UsuarioSistema) principal;
		} catch (ClassCastException e) {
			throw new RuntimeException("Erro interno de segurança: Tipo de usuário inválido na sessão.");
		}

		String role = usuarioLogado.getRole().getNome();

		if (!"ADMIN".equalsIgnoreCase(role)) {
			Integer idDonoDaReserva = entity.getUsuario().getId_usuario();
			Integer idUsuarioLogado = usuarioLogado.getId_usuario();

			if (!idDonoDaReserva.equals(idUsuarioLogado)) {
				throw new AccessDeniedException("Acesso negado: Você só pode excluir suas próprias reservas.");
			}
		}
		reservaRepository.delete(entity);
	}
}
