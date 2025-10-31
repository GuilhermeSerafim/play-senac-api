package com.playsenac.api.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "quadra")
public class QuadraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
    private String status;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "horario_abertura", nullable = false)
    private LocalTime horarioAbertura;

    @Column(name = "horario_fechamento", nullable = false)
    private LocalTime horarioFechamento;

    @Column(name = "limite_jogadores", nullable = false)
    private Integer limiteJogadores;

    private boolean interna;

    @OneToMany(mappedBy = "quadra")
    private List<ReservaEntity> reservas;


    public QuadraEntity() {
    }

    public QuadraEntity(String nome, String status, Integer diaSemana, LocalTime horarioAbertura, LocalTime horarioFechamento, Integer limiteJogadores, boolean interna, List<ReservaEntity> reservas) {
        this.nome = nome;
        this.status = status;
        this.diaSemana = diaSemana;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.limiteJogadores = limiteJogadores;
        this.interna = interna;
        this.reservas = reservas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
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

    public List<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }
}
