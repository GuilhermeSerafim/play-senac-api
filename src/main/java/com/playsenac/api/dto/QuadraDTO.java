package com.playsenac.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

public class QuadraDTO {

	private int id;
	
    @NotBlank
    @Size(min = 3)
    private String nome;

    private boolean status;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;

    private Integer limiteJogadores;

    private boolean interna;
    
    private List<Integer> diasSemana;

//    public QuadraDTO(@NotBlank @Size(min = 3) String nome, boolean status,
//                     @NotNull LocalTime horarioAbertura, @NotNull LocalTime horarioFechamento, Integer limiteJogadores,
//                     boolean interna, List<Integer> diasSemana) {
//        this.nome = nome;
//        this.status = status;
//        this.horarioAbertura = horarioAbertura;
//        this.horarioFechamento = horarioFechamento;
//        this.limiteJogadores = limiteJogadores;
//        this.interna = interna;
//        this.diasSemana = diasSemana;
//    }

	public QuadraDTO(Integer id, String nome, boolean status, Integer limiteJogadores, boolean interna) {
		this.id = id;
		this.nome = nome;
	    this.status = status;
	    this.limiteJogadores = limiteJogadores;
	    this.interna = interna;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public boolean isInterna() {
        return interna;
    }

    public void setInterna(boolean interna) {
        this.interna = interna;
    }

    public boolean isStatus(){
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public List<Integer> getDiasSemana() {
		return diasSemana;
	}

	public void setDiasSemana(List<Integer> diasSemana) {
		this.diasSemana = diasSemana;
	}
}
