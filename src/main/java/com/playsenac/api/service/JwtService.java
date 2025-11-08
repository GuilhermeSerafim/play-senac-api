package com.playsenac.api.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.playsenac.api.security.Role;
import com.playsenac.api.security.UsuarioSistema;

@Service
public class JwtService {
	
	@Autowired
	private JwtEncoder encode;
	
	@Autowired
	private JwtDecoder decode;
	
	private int tempoExpiracao = 10;
	
	public String gerarTokenJwt(UsuarioSistema usuario) {
		Instant agora = Instant.now();
		Instant expiracao = agora.plus(tempoExpiracao, ChronoUnit.MINUTES);
		String permissao = usuario.getRole().getNome();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("ver")
				.issuedAt(agora)
				.expiresAt(expiracao)
				.subject(usuario.getNome())
				.claim("email", usuario.getEmail())
				.claim("permissao", permissao)
				.build();
		
		JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
		try {
            org.springframework.security.oauth2.jwt.Jwt jwt = this.encode.encode(JwtEncoderParameters.from(jwsHeader, claims));
            return ((OAuth2Token) jwt).getTokenValue();
		} catch (JwtEncodingException err) {
			throw new RuntimeException("Erro ao gerar token JWT", err);
		}
	}
	
	public org.springframework.security.oauth2.jwt.Jwt decodificarToken(String token) throws JwtException {
		try {
			return this.decode.decode(token);
		} catch (BadJwtException e) {
			throw e;
		} catch (JwtException e) {
			throw e;
		}
	}
	
	public UsuarioSistema obterUsuarioDoToken(String token) {
		try {
			org.springframework.security.oauth2.jwt.Jwt jwt = decodificarToken(token);
			final String nome = jwt.getSubject();
			
			Role permissao = new Role();
			permissao.setNome(jwt.getClaimAsString("permissao"));
			UsuarioSistema usuarioSistema = new UsuarioSistema(nome, jwt.getClaimAsString("email"), null, permissao);
			return usuarioSistema;
		} catch(JwtException err) {
			//log.warn("Validação do JWT falhou: {}", err.getMessage());
		}
		return null;
	}
}
