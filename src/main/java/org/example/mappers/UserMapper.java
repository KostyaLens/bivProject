package org.example.mappers;

import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.dto.UserUpdateDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> userList);
    
    User toEntity(UserCreationDto userDto);

    User updateUserFromDto(UserUpdateDto userDto, @MappingTarget User user);
}
