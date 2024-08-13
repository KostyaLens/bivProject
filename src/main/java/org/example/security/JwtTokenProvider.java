package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.entity.Role;
import org.example.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    @Value("${security.jwt.secret}")
    private String key;

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(long userId, String username, String fio, Role role) {
        Claims claims = Jwts.claims().subject(username).add("id", userId).add("fio", fio)
                .add("role", "ROLE_" + role.name()).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(getKey()).compact();
    }

    public String createRefreshToken(long userId, String username) {
        Claims claims = Jwts.claims().subject(username).add("id", userId).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(getKey()).compact();
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().after(new Date());
    }
}
