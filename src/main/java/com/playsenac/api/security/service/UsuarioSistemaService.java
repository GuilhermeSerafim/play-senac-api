package com.playsenac.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioSistemaService implements UserDetailsService {
	
	@Autowired
	private usuarioRepository repository;
	
	public UsuarioSistemaService() { }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = repository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return user;
	}
	
	
}
