package com.playsenac.api.security;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private int id_role;
	
	@Column(name = "nome", unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "role")
	private Set<UsuarioSistema> usuarios;
	
	public Role() { }

	public int getId_role() {
		return id_role;
	}

	public void setId_role(int id_role) {
		this.id_role = id_role;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Set<UsuarioSistema> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<UsuarioSistema> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		return nome.toUpperCase();
	}
	
	
}
