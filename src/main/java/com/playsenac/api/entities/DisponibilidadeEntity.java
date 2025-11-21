package com.playsenac.api.entities;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "disponibilidade_quadra")
public class DisponibilidadeEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "horario_inicio")
	private LocalTime horarioAbertura;
	
	@Column(name = "horario_fim")
	private LocalTime horarioFechamento;

	@Column(name = "dia")
	private int dia;
	
	@ManyToOne
	@JoinColumn(name = "quadra_id", nullable = false)
	private QuadraEntity quadra;

	public DisponibilidadeEntity(LocalTime horarioAbertura, LocalTime horarioFechamento, int dia, QuadraEntity quadra) {
		this.horarioAbertura = horarioAbertura;
		this.horarioFechamento = horarioFechamento;
		this.dia = dia;
		this.quadra = quadra;
	}

	public DisponibilidadeEntity() { }
	
	public LocalTime getHorarioAbertura() {
		return horarioAbertura;
	}

	public void setHorarioAbertura(LocalTime horarioAbertura) {
		this.horarioAbertura = horarioAbertura;
	}

	public LocalTime getHorarioFechamento() {
		return horarioFechamento;
	}

	public void setHorarioFechamento(LocalTime horarioFechamento) {
		this.horarioFechamento = horarioFechamento;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public QuadraEntity getQuadra() {
		return quadra;
	}

	public void setQuadra(QuadraEntity quadra) {
		this.quadra = quadra;
	}
	
	
}
