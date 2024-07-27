package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreationDto;
import org.example.entity.User;
import org.example.exception.NotFoundUserException;
import org.example.mappers.UserMapper;
import org.example.security.IAuthenticationFacade;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AccountService accountService;

    private final UserMapper userMapper;

    private final IAuthenticationFacade authenticationFacade;

    @GetMapping("info")
    public User getUser() throws NotFoundUserException {
        return userService.getByUsername(authenticationFacade.getAuthentication().getName());
    }

    @DeleteMapping("/delete")
    public void deleteUser() throws NotFoundUserException {
        userService.deleteById(userService.getByUsername(authenticationFacade.getAuthentication().getName()).getId());
    }

    @PutMapping("/Update")
    public User updateUser(@RequestBody UserCreationDto userDTO) throws NotFoundUserException {
        User user = userService.getByUsername(authenticationFacade.getAuthentication().getName());
        userService.update(user);
        return user;
    }

}
