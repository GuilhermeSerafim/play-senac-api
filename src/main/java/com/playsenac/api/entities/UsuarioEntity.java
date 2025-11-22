package com.playsenac.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Usuario")
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", unique = true)
	private int id_usuario;

	@Column(name="nome", nullable = false)
	private String nome;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="senha", nullable = false)
	private String senha;

	@Column(name="telefone")
	private String telefone;

    @Column(name="fk_role")
    private int fk_role;

    @JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<ReservaEntity> reservas = new ArrayList<>();

    public UsuarioEntity() {}

	public UsuarioEntity(int id_usuario, String nome, String email, String senha, String telefone) {
		this.id_usuario = id_usuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
	}

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getFkRole() {
        return fk_role;
    }
    public void setFkRole(int fk_role) {
        this.fk_role = fk_role;
    }

    public List<ReservaEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntity> reservas) {
        this.reservas = reservas;
    }
}
