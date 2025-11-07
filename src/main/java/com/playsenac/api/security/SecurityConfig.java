package com.playsenac.api.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.playsenac.api.service.UsuarioSistemaService;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
		BCryptPasswordEncoder bcryptEnc = new BCryptPasswordEncoder();
		encoders.put("bycrypt", bcryptEnc);
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		var passwordEncoder = new DelegatingPasswordEncoder("bycrypt", encoders);
		/* na linha x deve ser trocada para passwordEncoder.setDefaultPasswordEncoderForMatches(bcryptEnc).
		 * para realizar a validação da senha com criptografia, atualmente compara texto simples como .equals;
		 * */
		passwordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
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
						.requestMatchers("/login", "/login.html", "/me.html",
                                "/h2-console/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
						.permitAll()
						.requestMatchers("/admin").hasAuthority("ADMIN")
						.anyRequest().authenticated())
				.build();
	}
}
