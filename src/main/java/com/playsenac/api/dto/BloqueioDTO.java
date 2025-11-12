package com.playsenac.api.dto;

import java.time.LocalDateTime;

import com.playsenac.api.entities.BloqueioEntity;

import jakarta.validation.constraints.NotNull;

public class BloqueioDTO {

    @NotNull(message = "ID é Obrigatório")
    private Integer id;

    @NotNull(message = "ID da Quadra é Obrigatório")
    private Integer idQuadraBloqueada;

    @NotNull(message = "Data e hora de início são obrigatórias")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data e hora de fim são obrigatórias")
    private LocalDateTime dataHoraFim;

    public BloqueioDTO() {
    }

    public BloqueioDTO(@NotNull(message = "ID é Obrigatório") Integer id,
            @NotNull(message = "ID da Quadra é Obrigatório") Integer idQuadraBloqueada,
            @NotNull(message = "Data e hora de início são obrigatórias") LocalDateTime dataHoraInicio,
            @NotNull(message = "Data e hora de fim são obrigatórias") LocalDateTime dataHoraFim) {
        this.id = id;
        this.idQuadraBloqueada = idQuadraBloqueada;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
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

    public Integer getIdQuadraBloqueada() {
        return idQuadraBloqueada;
    }

    public void setIdQuadraBloqueada(Integer idQuadraBloqueada) {
        this.idQuadraBloqueada = idQuadraBloqueada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static BloqueioDTO fromEntity(BloqueioEntity entity) {

        return new BloqueioDTO(
            entity.getId_bloqueio(),
            entity.getQuadraBloqueada().getId(), 
            entity.getDataHoraInicio(), 
            entity.getDataHoraFim());
    }
}
