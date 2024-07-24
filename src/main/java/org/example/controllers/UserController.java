package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.mappers.AccountMapper;
import org.example.mappers.UserMapper;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.example.dto.UserTDO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AccountService accountService;

    private final UserMapper userMapper;

    private final AccountMapper accountMapper;

    @PostMapping("/add")
    public void registrationUser(@RequestBody @Valid UserTDO userTDO){
        userTDO.setAccountDTO(accountMapper.toDto(accountService.createAccount(new Account())));
        userService.save(userMapper.toEntity(userTDO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id){
        try {
            User user = userService.getUserById(id).orElseThrow();
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id){
        accountService.deleteById(id);
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserTDO userDTO, @PathVariable long id){
        try {
            User user = userMapper.updateUserFromDto(userDTO, userService.getUserById(id).orElseThrow());
            userService.update(user, id);
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
