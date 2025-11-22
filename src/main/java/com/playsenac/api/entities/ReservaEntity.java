package com.playsenac.api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_reserva;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "fk_quadra", nullable = false)
    private QuadraEntity quadra;

    public ReservaEntity() {
    }

    public ReservaEntity(Integer id_reserva, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, boolean status, UsuarioEntity usuario, QuadraEntity quadra) {
        this.id_reserva = id_reserva;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = status;
        this.usuario = usuario;
        this.quadra = quadra;
    }

    public Integer getIdreserva() {
        return id_reserva;
    }

    public Integer getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Integer id_reserva) {
        this.id_reserva = id_reserva;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public QuadraEntity getQuadra() {
        return quadra;
    }

    public void setQuadra(QuadraEntity quadra) {
        this.quadra = quadra;
    }

}
