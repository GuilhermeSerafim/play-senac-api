package com.playsenac.api.dto;

import java.time.LocalDateTime;

import com.playsenac.api.entities.BloqueioEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BloqueioDTO {

    @NotNull(message = "Data e hora de início são obrigatórias")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data e hora de fim são obrigatórias")
    private LocalDateTime dataHoraFim;
    
    @NotBlank
    private String motivo;
    
    @NotNull
    private int idUsuario;
    
    @NotNull
    private int idQuadra;

    public BloqueioDTO() {
    }

	public BloqueioDTO(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String motivo, int idUsuario, int idQuadra) {
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.motivo = motivo;
		this.idUsuario = idUsuario;
		this.idQuadra = idQuadra;
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdQuadra() {
		return idQuadra;
	}

	public void setIdQuadra(int idQuadra) {
		this.idQuadra = idQuadra;
	}
	
    public static BloqueioDTO fromEntity(BloqueioEntity entity) {

        return new BloqueioDTO(
        		entity.getDataHoraInicio(),
        		entity.getDataHoraFim(),
        		entity.getMotivo(),
        		entity.getUsuarioBloqueador().getId_usuario(),
        		entity.getQuadra().getId_quadra()
            );
    }
	
}
