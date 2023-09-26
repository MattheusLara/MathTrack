package com.solucoesludicas.mathtrack.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.solucoesludicas.mathtrack.models.UserModel;
import com.solucoesludicas.mathtrack.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("mathtrack-api").withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate()).sign(algorithm);
            return token;
        } catch (JWTCreationException exception ) {
            throw new RuntimeException("Error while generate token!", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("mathtrack-api").build()
                    .verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Date generateExpirationDate(){
        return Date.from(LocalDateTime.now().plusHours(2).atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }
}
