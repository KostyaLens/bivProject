package org.example.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.entity.User;
import org.example.mappers.JwtMapper;
import org.example.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final UserService userService;

    private final JwtMapper jwtMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        return jwtMapper.toJwt(user);
    }

}
