package com.basicTransaction_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.basicTransaction_api.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class TokenHandler {

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256("1234");
            return JWT.create()
                    .withIssuer("Transaction_api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresToken())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Falha ao criar o Token", ex);
        }
    }

    private Instant expiresToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
