package com.jm.hellopay.security;

import com.jm.hellopay.configuration.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.jm.hellopay.model.enums.JwtClaim.TOKEN_TYPE;
import static com.jm.hellopay.model.enums.JwtTokenType.ACCESS;
import static com.jm.hellopay.model.enums.JwtTokenType.REFRESH;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails,
                Map.of(TOKEN_TYPE.getValue(), ACCESS.getValue()),
                (1000 * jwtProperties.expiration().access()));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails,
                Map.of(TOKEN_TYPE.getValue(), REFRESH.getValue()),
                (1000 * jwtProperties.expiration().refresh()));
    }

    public String buildToken(UserDetails userDetails, Map<String, Object> claims,
                             long expirationMs) {
        return Jwts.builder()
                .issuer(jwtProperties.issuer())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .subject(userDetails.getUsername())
                .claims(claims)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE.getValue()).toString());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = parse(token);
        return claimsResolver.apply(claims);
    }

    private Claims parse(String jwt) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateAccessToken(String jwt, UserDetails userDetails) {
        return validateToken(jwt, userDetails, ACCESS.getValue());
    }

    private boolean validateToken(String jwt, UserDetails userDetails, String requiredTokenType) {
        final String username = extractUsername(jwt);
        final var tokenType = extractTokenType(jwt);

        return (requiredTokenType.equals(tokenType)
                && username.equals(userDetails.getUsername())
                && !isTokenExpired(jwt));
    }

}
