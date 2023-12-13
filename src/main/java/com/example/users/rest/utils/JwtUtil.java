package com.example.users.rest.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 7200000); // Caducidad del token

        return Jwts
                .builder()
                .subject(email)
                .expiration(expiryDate)
                .issuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }



}
