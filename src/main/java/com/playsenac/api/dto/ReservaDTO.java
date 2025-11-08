package com.playsenac.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;
import com.playsenac.api.entities.UsuarioEntity;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // para não aparecer nenhum campo NULL na resposta
public class ReservaDTO {
     private Integer id;

    @NotNull(message = "Data e hora de início são obrigatórias")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data e hora de fim são obrigatórias")
    private LocalDateTime dataHoraFim;

    private String status; // manter em response DTO

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotNull(message = "ID da quadra é obrigatório")
    private Integer idQuadra;

    private String nomeQuadra;

    private Integer qtdConvidados;

    public ReservaDTO() {
    }

    public ReservaDTO(Integer idReserva, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String status, Integer idUsuario, Integer idQuadra, String nomeQuadra) {
        this.id = idReserva;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = status;
        this.idUsuario = idUsuario;
        this.idQuadra = idQuadra;
        this.nomeQuadra = nomeQuadra;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdQuadra() {
        return idQuadra;
    }

    public void setIdQuadra(Integer idQuadra) {
        this.idQuadra = idQuadra;
    }

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    public Integer getQtdConvidados() {
        return qtdConvidados;
    }

    public void setQtdConvidados(Integer qtdConvidados) {
        this.qtdConvidados = qtdConvidados;
    }

    public static ReservaDTO fromEntity(ReservaEntity entity){
        return new ReservaDTO(
                entity.getId_reserva(),
                entity.getDataHoraInicio(),
                entity.getDataHoraFim(),
                entity.getStatus(),
                entity.getUsuario() != null ? entity.getUsuario().getId_usuario() : null,
                entity.getQuadra() != null ? entity.getQuadra().getId() : null,
                entity.getQuadra() != null ? entity.getQuadra().getNome() : null
        );
    }

    public ReservaEntity toEntity(UsuarioEntity usuario, QuadraEntity quadra){
        ReservaEntity entity = new ReservaEntity();
        entity.setId_reserva(this.id);
        entity.setDataHoraInicio(this.dataHoraInicio);
        entity.setDataHoraFim(this.dataHoraFim);
        entity.setStatus(this.status);
        entity.setUsuario(usuario);
        entity.setQuadra(quadra);
        return entity;
    }

}
