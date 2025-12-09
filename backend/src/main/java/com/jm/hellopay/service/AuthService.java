package com.jm.hellopay.service;

import com.jm.hellopay.model.dto.AuthResponse;
import com.jm.hellopay.model.dto.LoginRequest;
import com.jm.hellopay.model.dto.RegisterRequest;
import com.jm.hellopay.model.enums.Role;
import com.jm.hellopay.repository.UserRepository;
import com.jm.hellopay.model.entity.User;
import com.jm.hellopay.security.CustomUserDetails;
import com.jm.hellopay.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {

        if (repository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateKeyException("Email already registered.");
        }

        final var user = new User();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(Role.USER.name());

        repository.save(user);

        final var details = new CustomUserDetails(user);

        return new AuthResponse(
                jwtService.generateAccessToken(details),
                jwtService.generateRefreshToken(details)
        );
    }

    public AuthResponse login(LoginRequest request) {

        final var authentication = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        authManager.authenticate(authentication);

        final var user = userDetailsService.loadUserByUsername(request.email());

        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    public AuthResponse refresh(String refreshToken) {

        final var email = jwtService.extractUsername(refreshToken);
        final var user = userDetailsService.loadUserByUsername(email);

        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }
}
