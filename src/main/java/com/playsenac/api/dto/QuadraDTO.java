package com.playsenac.api.dto;

import com.playsenac.api.entities.QuadraEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

public class QuadraDTO {

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
    
    public QuadraDTO(@NotBlank @Size(min = 3) String nome, boolean status,
                     @NotNull LocalTime horarioAbertura, @NotNull LocalTime horarioFechamento, Integer limiteJogadores,
                     boolean interna) {
        this.nome = nome;
        this.status = status;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.limiteJogadores = limiteJogadores;
        this.interna = interna;
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

    public static QuadraDTO fromEntity(QuadraEntity entity) {
        return new QuadraDTO(
                entity.getNome(),
                entity.isStatus(),
                entity.getHorarioAbertura(),
                entity.getHorarioFechamento(),
                entity.getLimiteJogadores(),
                entity.isInterna()
        );
    }

    public QuadraEntity toEntity() {
        QuadraEntity entity = new QuadraEntity();
        entity.setNome(this.getNome());
        entity.setStatus(this.isStatus());
        entity.setHorarioAbertura(this.getHorarioAbertura());
        entity.setHorarioFechamento(this.getHorarioFechamento());
        entity.setLimiteJogadores(this.getLimiteJogadores());
        entity.setInterna(this.isInterna());
        return entity;
    }



}
