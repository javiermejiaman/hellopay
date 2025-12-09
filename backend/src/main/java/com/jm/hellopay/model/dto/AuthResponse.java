package com.jm.hellopay.model.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
