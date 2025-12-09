package com.jm.hellopay.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stripe")
public record StripeProperties(
        String apiKey,
        Webhooks webhooks) {
    public record Webhooks(String success, String cancel) {}
}
