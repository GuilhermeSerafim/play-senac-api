package com.playsenac.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quadra")
public class QuadraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_quadra;

    @Column(unique = true, nullable = false)
    private String nome;

    private boolean status;

    @Column(name = "limite_jogadores", nullable = false)
    private Integer limiteJogadores;

    private boolean interna;

    @OneToMany(mappedBy = "quadra")
    private List<ReservaEntity> reservas;

    @OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibilidadeEntity> disponibilidades = new ArrayList();

    public QuadraEntity() {
    }

    public QuadraEntity(String nome, Integer limiteJogadores, boolean interna, List<ReservaEntity> reservas) {
        this.nome = nome;
        this.limiteJogadores = limiteJogadores;
        this.interna = interna;
        this.reservas = reservas;
    }

    public Integer getId() {
        return id_quadra;
    }

    public void setId(Integer id) {
        this.id_quadra = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLimiteJogadores() {
        return limiteJogadores;
    }

    public void setLimiteJogadores(Integer limiteJogadores) {
        this.limiteJogadores = limiteJogadores;
    }

    public boolean isInterna() {
        return interna;
    }

    public void setInterna(boolean interna) {
        this.interna = interna;
    }

     public List<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public Integer getId_quadra() {
		return id_quadra;
	}

	public void setId_quadra(Integer id_quadra) {
		this.id_quadra = id_quadra;
	}

	public List<DisponibilidadeEntity> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<DisponibilidadeEntity> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}
    
    
}
