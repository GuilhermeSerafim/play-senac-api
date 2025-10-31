package com.playsenac.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class QuadraDTO {

    @NotBlank
    @Size(min = 3)
    private String nome;

    @NotBlank
    private String status;

    @NotNull
    private Integer diaSemana;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;

    private Integer limiteJogadores;

    private boolean interna;

}
