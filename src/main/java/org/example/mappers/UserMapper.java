package org.example.mappers;

import org.example.dto.AccountDTO;
import org.example.dto.UserTDO;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "accountDTO", source = "account")
    UserTDO toDto(User user);

    @Mapping(target = "account", source = "accountDTO")
    User toEntity(UserTDO userTDO);

    @Mapping(target = "dateOfCreating", ignore = true)
    User updateUserFromDto(UserTDO userTDO, @MappingTarget User user);
}
