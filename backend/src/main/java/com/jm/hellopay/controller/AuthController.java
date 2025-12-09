package com.jm.hellopay.controller;

import com.jm.hellopay.model.dto.AuthResponse;
import com.jm.hellopay.model.dto.LoginRequest;
import com.jm.hellopay.model.dto.RegisterRequest;
import com.jm.hellopay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        return authService.refresh(token);
    }
}
