package com.playsenac.api.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.playsenac.api.service.UsuarioSistemaService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
		BCryptPasswordEncoder bcryptEnc = new BCryptPasswordEncoder();
		encoders.put("bycrypt", bcryptEnc);
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		var passwordEncoder = new DelegatingPasswordEncoder("bycrypt", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(bcryptEnc);
		return passwordEncoder;
	}
	
	@Bean
	AuthenticationManager authenticationManager(UsuarioSistemaService service, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(service);
		authProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authProvider);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.cors(cors -> Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(fo -> fo.sameOrigin()))
				.formLogin(formLogin -> formLogin.disable())
				.authorizeHttpRequests(authorize -> authorize
						//requisições sem a necessidade de autenticação ou autorização
						.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/usuarios/cadastro").permitAll()
						.requestMatchers("/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/quadras").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuarios/buscar").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/usuarios/atualizar/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/usuarios/deletar/**").permitAll()
						
						.requestMatchers("/reservas/**").permitAll()
						
						//requisições exclusivas do administrador
						.requestMatchers("/quadras/**").hasAuthority("ADMIN")
						.requestMatchers("/bloqueios/**").hasAuthority("ADMIN")
						
						//requisições exclusivas do usuario
						.requestMatchers(HttpMethod.PUT ,"/usuarios/**").hasAuthority("COMUM")
						.requestMatchers(HttpMethod.DELETE ,"/usuarios/**").hasAuthority("COMUM")
						
						.anyRequest().authenticated()
						)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}
