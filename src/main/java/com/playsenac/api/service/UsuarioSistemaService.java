package com.playsenac.api.service;


import com.playsenac.api.repository.UsuarioSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.playsenac.api.repository.UsuarioSistemaRepository;

@Service
public class UsuarioSistemaService implements UserDetailsService {
	
	@Autowired
	private UsuarioSistemaRepository repository;
	
	public UsuarioSistemaService() { }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("Usuário não encontrado com o email: " + username)
                );
	}
	
	
}
