package com.playsenac.api.dto;

import com.playsenac.api.entities.ConvidadoEntity;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaDTO {

    @NotNull(message = "Data e hora de início são obrigatórias")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data e hora de fim são obrigatórias")
    private LocalDateTime dataHoraFim;

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotNull(message = "ID da quadra é obrigatório")
    private Integer idQuadra;

    private List<ConvidadoEntity> convidados;
    
   public ReservaDTO() { }
    
    public ReservaDTO(
			@NotNull(message = "Data e hora de início são obrigatórias") LocalDateTime dataHoraInicio,
			@NotNull(message = "Data e hora de fim são obrigatórias") LocalDateTime dataHoraFim,
			@NotNull(message = "ID do usuário é obrigatório") Integer idUsuario,
			@NotNull(message = "ID da quadra é obrigatório") Integer idQuadra, List<ConvidadoEntity> convidados) {
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.idUsuario = idUsuario;
		this.idQuadra = idQuadra;
		this.convidados = convidados;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Integer idUsuario) {
	    this.idUsuario = idUsuario;
	}

	public void setIdQuadra(Integer idQuadra) {
	    this.idQuadra = idQuadra;
	}

	public Integer getIdQuadra() {
		return idQuadra;
	}

	public List<ConvidadoEntity> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<ConvidadoEntity> convidados) {
		this.convidados = convidados;
	}

}
