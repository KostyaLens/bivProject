package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.JwtRequest;
import org.example.dto.JwtResponse;
import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.mappers.UserMapper;
import org.example.services.AuthService;
import org.example.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization Controller", description = "Authorization API")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public JwtResponse login(@RequestBody @Valid JwtRequest loginRequest) throws NotFoundUserOrAccountException {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Registration")
    public UserDto register(@Valid @RequestBody UserCreationDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.save(user);
        return userMapper.toDto(user);
    }
}
