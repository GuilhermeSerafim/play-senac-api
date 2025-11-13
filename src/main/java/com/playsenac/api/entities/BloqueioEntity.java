package com.playsenac.api.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "quadras_bloqueadas")
public class BloqueioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_bloqueio;

    @Column(name = "Data_hora_Inicio", nullable = false)
    private LocalDateTime dataHoraInicio;
    
    
    
    @Column(name = "Data_hora_Fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "id_quadra", nullable = false)
    private QuadraEntity quadraBloqueada;

    public BloqueioEntity() {
    }

    public BloqueioEntity(Integer id_bloqueio, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim,
            QuadraEntity quadraBloqueada) {
        this.id_bloqueio = id_bloqueio;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.quadraBloqueada = quadraBloqueada;
    }

    public Integer getId_bloqueio() {
        return id_bloqueio;
    }

    public void setId_bloqueio(Integer id_bloqueio) {
        this.id_bloqueio = id_bloqueio;
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

    public QuadraEntity getQuadraBloqueada() {
        return quadraBloqueada;
    }

    public void setQuadraBloqueada(QuadraEntity quadraBloqueada) {
        this.quadraBloqueada = quadraBloqueada;
    }


    
}
