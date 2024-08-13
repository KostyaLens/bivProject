package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.SortingException;
import org.example.mappers.UserMapper;
import org.example.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "Admin API")
public class AdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    private static final String DEFAULT_SORTING_DIRECTION = "ASC";

    private static final String DEFAULT_SORTING_FIELD = "id";

    @GetMapping("/list-users")
    @Operation(summary = "Administration method, get list all users")
    public List<UserDto> getAllUsers(@RequestParam int page,
                                     @RequestParam int count,
                                     @RequestParam(required = false) List<String> sortingField,
                                     @RequestParam(required = false) List<String> sortingDirection
    ) throws SortingException {
        if (sortingDirection == null){
            sortingDirection = new ArrayList<>();
            sortingDirection.add(DEFAULT_SORTING_DIRECTION);
        }
        if (sortingField == null){
            sortingField = new ArrayList<>();
            sortingField.add(DEFAULT_SORTING_FIELD);
        }
        List<User> users = userService.getAllUsers(page, count, sortingField, sortingDirection).getContent();
        return userMapper.toDtoList(users);
    }
}
