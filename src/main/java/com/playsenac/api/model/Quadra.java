package com.playsenac.api.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "quadra")
public class Quadra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_quadra;
    private String nome;
    private String status;
    private Integer dia_semana;
    private LocalTime horario_abertura;
    private LocalTime horario_fechamento;

    public Quadra() {
    }

    public Quadra(Integer id_quadra, String nome, String status, Integer dia_semana, LocalTime horario_abertura, LocalTime horario_fechamento) {
        this.id_quadra = id_quadra;
        this.nome = nome;
        this.status = status;
        this.dia_semana = dia_semana;
        this.horario_abertura = horario_abertura;
        this.horario_fechamento = horario_fechamento;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(Integer dia_semana) {
        this.dia_semana = dia_semana;
    }

    public LocalTime getHorario_abertura() {
        return horario_abertura;
    }

    public void setHorario_abertura(LocalTime horario_abertura) {
        this.horario_abertura = horario_abertura;
    }

    public LocalTime getHorario_fechamento() {
        return horario_fechamento;
    }

    public void setHorario_fechamento(LocalTime horario_fechamento) {
        this.horario_fechamento = horario_fechamento;
    }
}
