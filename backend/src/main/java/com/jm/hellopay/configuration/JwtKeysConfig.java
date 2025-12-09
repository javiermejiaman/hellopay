package com.jm.hellopay.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class JwtKeysConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public PrivateKey privateKey() throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        final var decodedKey = Base64.getDecoder().decode(
                jwtProperties.keys().privateKey());
        final var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
    }

    @Bean
    public PublicKey publicKey() throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        final var decodedKey = Base64.getDecoder().decode(
                jwtProperties.keys().publicKey());
        final var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
    }

}
