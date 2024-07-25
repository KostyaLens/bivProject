package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreationDto;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.exception.NotEnoughFundsException;
import org.example.exception.NotFoundUserException;
import org.example.mappers.AccountMapper;
import org.example.mappers.UserMapper;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.example.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AccountService accountService;

    private final UserMapper userMapper;

//    @PostMapping("/add")
//    public void registrationUser(@RequestBody @Valid UserCreationDto userDto){
//        User user = userMapper.toEntity(userDto);
//        user.setAccount(accountService.createAccount(new Account()));
//        userService.save(user);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) throws NotFoundUserException {
        try {
            User user = userService.getUserById(id).orElseThrow();
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }
        catch (Exception e){
            throw new NotFoundUserException("Пользователь не найден");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id){
        accountService.deleteById(id);
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserCreationDto userDTO, @PathVariable long id) throws NotFoundUserException {
        try {
            User user = userMapper.updateUserFromDto(userDTO, userService.getUserById(id).orElseThrow());
            userService.update(user);
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }
        catch (Exception e){
                throw new NotFoundUserException("Пользователь не найден");
        }
    }

}
