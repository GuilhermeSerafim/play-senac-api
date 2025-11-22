package com.playsenac.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "convidado")
public class ConvidadoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_convidado;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne
	@JoinColumn(name = "fk_reserva", nullable = false)
	private ReservaEntity reserva;

	public ConvidadoEntity(String nome, String telefone, String email, ReservaEntity reserva) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.reserva = reserva;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ReservaEntity getReserva() {
		return reserva;
	}

	public void setReserva(ReservaEntity reserva) {
		this.reserva = reserva;
	}
}
