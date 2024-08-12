package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entity.Role;
import org.example.properties.JwtProperties;
import org.example.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final JwtUserDetailService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(long userId, String username, String fio, Role role) {
        Claims claims = Jwts.claims().subject(username).add("id", userId).add("fio", fio)
                .add("role", "ROLE_"+role.name()).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(key).compact();
    }

    public String createRefreshToken(long userId, String username) {
        Claims claims = Jwts.claims().subject(username).add("id", userId).build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().claims(claims).expiration(Date.from(validity.toInstant())).signWith(key).compact();
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().after(new Date());
    }

    private String getId(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("id", String.class);
    }

    private String getUsername(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
