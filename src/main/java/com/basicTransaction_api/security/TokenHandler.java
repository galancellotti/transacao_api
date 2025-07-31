package com.basicTransaction_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.basicTransaction_api.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenHandler {

    @Value("${api.secutiry.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Transaction_api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresToken())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Falha ao criar o Token", ex);
        }
    }

    public String verifyToken(String tokenJwt)  {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Transaction_api")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();


        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    private Instant expiresToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
