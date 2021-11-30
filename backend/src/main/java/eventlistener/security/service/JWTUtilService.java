/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JWTUtilService {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.secret.duration.hours}")
    private int JWT_DURATION_HOURS;

    public String createToken(Map<String, Object> claims, String toAuthorize) {
        //Generate JWT
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(toAuthorize)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(JWT_DURATION_HOURS))))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        Claims claim =Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return claim.getSubject();
    }
}
