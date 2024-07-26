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
    public ResponseEntity<?> getUser() throws NotFoundUserException {
        try {
            User user = userService.getByUsername(authenticationFacade.getAuthentication().getName());
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundUserException("Пользователь не найден");
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser() {
        userService.deleteById(userService.getByUsername(authenticationFacade.getAuthentication().getName()).getId());
    }

    @PutMapping("/Update")
    public ResponseEntity<?> updateUser(@RequestBody UserCreationDto userDTO) throws NotFoundUserException {
        try {
            User user = userMapper.updateUserFromDto(userDTO, userService.getByUsername(authenticationFacade.getAuthentication().getName()));
            userService.update(user);
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundUserException("Пользователь не найден");
        }
    }

}
