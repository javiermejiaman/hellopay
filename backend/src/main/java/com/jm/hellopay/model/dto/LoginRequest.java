package com.jm.hellopay.model.dto;

public record LoginRequest(
        String email,
        String password
) {}
