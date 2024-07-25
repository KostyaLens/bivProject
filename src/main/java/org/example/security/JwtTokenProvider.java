package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.dto.JwtResponse;
import org.example.entity.User;
import org.example.exception.NotFoundUserException;
import org.example.properties.JwtProperties;
import org.example.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(long userId, String username, String fio) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("fio", fio);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(key).compact();
    }

    public String createRefreshToken(long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(key).compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) throws NotFoundUserException {
        JwtResponse jwtResponse = new JwtResponse();
        if (!isValid(refreshToken)) {
            throw new NotFoundUserException("Такой пользователь не найден");
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getUserById(userId).orElseThrow();
        jwtResponse.setId(userId);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUsername(), user.getFio()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUsername()));
        return jwtResponse;
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id").toString();
    }

    private String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
