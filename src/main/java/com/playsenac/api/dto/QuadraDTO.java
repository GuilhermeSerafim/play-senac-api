package com.playsenac.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

public class QuadraDTO {
	
    @NotBlank
    @Size(min = 3)
    private String nome;

    private boolean isBloqueada;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;

    private Integer limiteJogadores;
    
    private String imagemUrl; 
    
    private List<Integer> diasSemana;
    
    public QuadraDTO() { }

    public QuadraDTO(String nome, boolean isBloqueada, LocalTime horarioAbertura, 
                     LocalTime horarioFechamento, Integer limiteJogadores, String imagemUrl, List<Integer> diasSemana) {
        this.nome = nome;
        this.isBloqueada = isBloqueada;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.limiteJogadores = limiteJogadores;
        this.imagemUrl = imagemUrl;
        this.diasSemana = diasSemana;
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

	public List<Integer> getDiasSemana() {
		return diasSemana;
	}

	public void setDiasSemana(List<Integer> diasSemana) {
		this.diasSemana = diasSemana;
	}
}