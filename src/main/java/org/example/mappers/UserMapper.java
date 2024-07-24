package org.example.mappers;

import org.example.dto.UserCreationDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "accountDTO", source = "account")
    UserDto toDto(User user);

    @Mapping(target = "account", source = "accountDTO")
    User toEntity(UserDto userDto);

    @Mapping(target = "account", source = "accountDTO")
    User toEntity(UserCreationDto userDto);

    User updateUserFromDto(UserCreationDto userDto, @MappingTarget User user);
}
