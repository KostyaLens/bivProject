package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.mappers.UserMapper;
import org.example.services.UserService;
import org.example.dto.UserTDO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/add")
    public void registrationUser(@RequestBody User user){
        userService.save(user);
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
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserTDO userDTO, @PathVariable long id){
        try {
            User user = userMapper.toEntity(userDTO);
            userService.update(user, id);
            return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
