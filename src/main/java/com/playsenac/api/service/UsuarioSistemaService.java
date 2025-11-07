package com.playsenac.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playsenac.api.entities.usuarioEntity;
import com.playsenac.api.repository.usuarioRepository;
import com.playsenac.api.security.Role;
import com.playsenac.api.security.UsuarioSistema;

@Service
public class UsuarioSistemaService implements UserDetailsService {
	
	@Autowired
	private usuarioRepository repository;
	
	public UsuarioSistemaService() { }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<usuarioEntity> user = repository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		Role role = new Role();
		role.setNome("ADMIN");
		UsuarioSistema us = null;
		us.setEmail(user.get().getEmail());
		us.setSenha(user.get().getSenha());
		us.setRole(role);
		return us;
	}
	
	
}
