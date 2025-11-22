package com.playsenac.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ConvidadoDTO {
	
	@NotBlank
	private String nome;

	@NotBlank
	private String telefone;
	
	@NotBlank
	private String email;

	public ConvidadoDTO() { } 
	
	public ConvidadoDTO(@NotBlank String nome, @NotBlank String telefone, @NotBlank String email) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
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
	
}
