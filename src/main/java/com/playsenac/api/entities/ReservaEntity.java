package com.playsenac.api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "fk_quadra", nullable = false)
    private QuadraEntity quadra;
    
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConvidadoEntity> convidados;

    public ReservaEntity() {
    }

    public ReservaEntity(Integer id_reserva, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, UsuarioEntity usuario, QuadraEntity quadra) {
        this.id_reserva = id_reserva;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity optional) {
        this.usuario = optional;
    }

    public QuadraEntity getQuadra() {
        return quadra;
    }

    public void setQuadra(QuadraEntity optional) {
        this.quadra = optional;
    }

	public List<ConvidadoEntity> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<ConvidadoEntity> convidados) {
		this.convidados = convidados;
	}

    
}
