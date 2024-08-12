package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.UserUpdateDto;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.exception.SortingException;
import org.example.mappers.UserMapper;
import org.example.security.AuthenticationFacade;
import org.example.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/info")
    @Operation(summary = "User information")
    public UserDto getUser() throws NotFoundUserOrAccountException {
        return userMapper.toDto(userService.getByUsername(authenticationFacade.getCurrentUserName()));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Deleting an authorized user")
    public void deleteUser() throws NotFoundUserOrAccountException {
        userService.deleteById(userService.getByUsername(authenticationFacade.getCurrentUserName()).getId());
    }

    @PutMapping("/update")
    @Operation(summary = "Updating an authorized user")
    public User updateUser(@RequestBody UserUpdateDto userDto) throws NotFoundUserOrAccountException {
        User user = userService.getByUsername(authenticationFacade.getCurrentUserName());
        userService.update(userMapper.updateUserFromDto(userDto, user));
        return user;
    }

    @GetMapping("/list-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Administration method, get list all users")
    public List<UserDto> getAllUsers(@RequestParam int page,
                                  @RequestParam int count,
                                  @RequestParam(required = false) List<String> sortingField,
                                  @RequestParam(required = false) List<String> sortingDirection
                                  ) throws SortingException {
        if (sortingDirection == null){
            sortingDirection = new ArrayList<>();
            sortingDirection.add("ascending");
        }
        if (sortingField == null){
            sortingField = new ArrayList<>();
            sortingField.add("id");
        }
        List<User> users = userService.getAllUsers(page, count, sortingField, sortingDirection).getContent();
        return userMapper.toDtoList(users);
    }

}
