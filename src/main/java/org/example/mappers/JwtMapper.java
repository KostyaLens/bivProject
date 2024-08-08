package org.example.mappers;

import org.example.entity.User;
import org.example.security.JwtEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {

    JwtEntity toJwt(User user);

    User toEntity(JwtEntity jwtEntity);
}
