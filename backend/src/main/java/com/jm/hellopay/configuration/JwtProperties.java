package com.jm.hellopay.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String issuer,
        Expiration expiration,
        Keys keys) {
    public record Expiration(long access, long refresh) {}
    public record Keys(String privateKey, String publicKey) {}
}
