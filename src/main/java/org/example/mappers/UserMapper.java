package org.example.mappers;

import org.example.dto.UserTDO;
import org.example.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserTDO toDto(User user);

    User toEntity(UserTDO userTDO);
}
