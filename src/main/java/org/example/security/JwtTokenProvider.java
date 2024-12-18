package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Data
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String KEY_FOR_USER_ID = "id";

    private static final String KEY_FOR_USER_FIO = "fio";

    private static final String KEY_FOR_USER_ROLE = "role";

    private static final String START_FOR_USER_ROLE = "ROLE_";

    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.access}")
    private long access;

    @Value("${security.jwt.refresh}")
    private long refresh;

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(long userId, String username, String fio, Role role) {
        Claims claims = Jwts.claims().subject(username).add(KEY_FOR_USER_ID, userId).add(KEY_FOR_USER_FIO, fio)
                .add(KEY_FOR_USER_ROLE, START_FOR_USER_ROLE + role.name()).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(getSigningKey()).compact();
    }

    public String createRefreshToken(long userId, String username) {
        Claims claims = Jwts.claims().subject(username).add(KEY_FOR_USER_ID, userId).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(getSigningKey()).compact();
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().after(new Date());
    }
}
