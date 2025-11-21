package com.playsenac.api.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {

	@Value("${TOKEN_CHAVE}")
	private String CHAVE;
	
	@Bean
	public SecretKey jwtSecretKey() {
		try {
			byte[] decodedKey = MessageDigest.getInstance("SHA-256")
					.digest(CHAVE.getBytes(StandardCharsets.UTF_8));
			
			if(decodedKey.length < 32) {
				throw new IllegalArgumentException("Token menor que 256 bits");
			}
			return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
		} catch(NoSuchAlgorithmException err) {
			throw new RuntimeException(err);
		} catch(IllegalArgumentException err) {
			throw new IllegalArgumentException(err);
		}
	}
	
	@Bean
	public JWKSource<SecurityContext> jwtSource(SecretKey key) {
		JWK jwk = new OctetSequenceKey.Builder(key).build();
		JWKSet jwkSet = new JWKSet(jwk);
		return new ImmutableJWKSet<SecurityContext>(jwkSet);
	}
	
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> source) {
		return new NimbusJwtEncoder(source);
	}
	
	@Bean
	public JwtDecoder jwtDecoder(SecretKey key) {
		return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256)
				.build();
	}
}
