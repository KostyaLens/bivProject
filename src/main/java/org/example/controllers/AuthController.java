package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.JwtRequest;
import org.example.dto.JwtResponse;
import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.NotFoundUserException;
import org.example.mappers.UserMapper;
import org.example.services.AccountService;
import org.example.services.AuthService;
import org.example.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest loginRequest) throws NotFoundUserException {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserCreationDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.save(user);
        return userMapper.toDto(user);
    }

    @PostMapping("/refersh")
    public JwtResponse refresh(@RequestBody String refreshToken) throws NotFoundUserException {
        return authService.refresh(refreshToken);
    }
}
