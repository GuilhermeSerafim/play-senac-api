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
    private String status;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario; // mudar depois de implementar classe usuario

    @ManyToOne
    @JoinColumn(name = "id_quadra", nullable = false)
    private QuadraEntity quadra;

    private Integer qtdConvidados; // mudar depois de implementar classe convidados

    public ReservaEntity() {
    }

    public ReservaEntity(Integer id_reserva, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String status, Integer idUsuario, QuadraEntity quadra, Integer qtdConvidados) {
        this.id_reserva = id_reserva;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = status;
        this.idUsuario = idUsuario;
        this.quadra = quadra;
        this.qtdConvidados = qtdConvidados;
    }

    public Integer getIdreserva() {
        return id_reserva;
    }

    public void setIdreserva(Integer id_reserva) {
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

    public QuadraEntity getQuadra() {
        return quadra;
    }

    public void setQuadra(QuadraEntity quadra) {
        this.quadra = quadra;
    }

    public Integer getQtdConvidados() {
        return qtdConvidados;
    }

    public void setQtdConvidados(Integer qtdConvidados) {
        this.qtdConvidados = qtdConvidados;
    }
}
