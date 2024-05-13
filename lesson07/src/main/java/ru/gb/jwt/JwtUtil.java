package ru.gb.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.*;

@Service
public class JwtUtil {

    private static final String SECRET_KEY =
            "super_secret_key_123456789_super_secret_key_123456789";

    public String issueToken(String subject) {
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims) {

        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuer("https://gb.ru/")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(5, YEARS)))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
