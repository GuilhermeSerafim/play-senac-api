package com.playsenac.api.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "quadra")
public class QuadraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_quadra;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(name = "is_bloqueada", nullable = false) 
    private boolean isBloqueada;

    @Column(name = "limite_jogadores", nullable = false)
    private Integer limiteJogadores;
    
    @Column(name = "imagem_url") 
    private String imagemUrl;

    @OneToMany(mappedBy = "quadra", cascade = CascadeType.REMOVE, orphanRemoval = true) 
    @JsonIgnore
    private List<ReservaEntity> reservas;
    
    @OneToMany(mappedBy = "quadra", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<BloqueioEntity> bloqueios;
    
    @OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibilidadeEntity> disponibilidades = new ArrayList<>();

    public QuadraEntity() {
    }
    
    public QuadraEntity(String nome, Integer limiteJogadores, boolean isBloqueada, String imagemUrl) {
        this.nome = nome;
        this.limiteJogadores = limiteJogadores;
        this.isBloqueada = isBloqueada;
        this.imagemUrl = imagemUrl;
    }

    public void setId(Integer id) {
        this.id_quadra = id;
    }
    
    public Integer getId_quadra() {
		return id_quadra;
	}

	public void setId_quadra(Integer id_quadra) {
		this.id_quadra = id_quadra;
	}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean isBloqueada() {
        return isBloqueada;
    }

    public void setBloqueada(boolean isBloqueada) {
        this.isBloqueada = isBloqueada;
    }

    public Integer getLimiteJogadores() {
        return limiteJogadores;
    }

    public void setLimiteJogadores(Integer limiteJogadores) {
        this.limiteJogadores = limiteJogadores;
    }
    
    public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

     public List<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }

    public List<BloqueioEntity> getBloqueios() {
        return bloqueios;
    }

    public void setBloqueios(List<BloqueioEntity> bloqueios) {
        this.bloqueios = bloqueios;
    }

	public List<DisponibilidadeEntity> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<DisponibilidadeEntity> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}
}