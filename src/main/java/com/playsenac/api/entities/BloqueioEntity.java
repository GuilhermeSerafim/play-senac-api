package com.playsenac.api.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "bloqueio")
public class BloqueioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_bloqueio;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;  
    
    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;
    
    @Column(name = "motivo", nullable = false)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private UsuarioEntity usuarioBloqueador;
    
    @ManyToOne
    @JoinColumn(name = "fk_quadra", nullable = false)
    private QuadraEntity quadraBloqueada;

    public BloqueioEntity() { }
    
    public BloqueioEntity(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String motivo, UsuarioEntity usuarioBloqueador, QuadraEntity quadraBloqueada) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.motivo = motivo;
        this.usuarioBloqueador = usuarioBloqueador;
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public UsuarioEntity getUsuarioBloqueador() {
		return usuarioBloqueador;
	}

	public void setUsuarioBloqueador(UsuarioEntity usuarioBloqueador) {
		this.usuarioBloqueador = usuarioBloqueador;
	}

	public QuadraEntity getQuadraBloqueada() {
		return quadraBloqueada;
	}

	public void setQuadraBloqueada(QuadraEntity quadraBloqueada) {
		this.quadraBloqueada = quadraBloqueada;
	}


}