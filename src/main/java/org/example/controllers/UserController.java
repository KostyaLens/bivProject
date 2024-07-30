package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.mappers.UserMapper;
import org.example.security.AuthenticationFacade;
import org.example.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/info")
    public UserDto getUser() throws NotFoundUserOrAccountException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        return userMapper.toDto(userService.getByUsername(authenticationFacade.getCurrentUserName()));
    }

    @DeleteMapping("/delete")
    public void deleteUser() throws NotFoundUserOrAccountException {
        userService.deleteById(userService.getByUsername(authenticationFacade.getCurrentUserName()).getId());
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody UserCreationDto userDTO) throws NotFoundUserOrAccountException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        userService.update(user);
        return user;
    }

}
