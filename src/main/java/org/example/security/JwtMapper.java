package org.example.security;

import org.example.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {

    JwtEntity toJwt(User user);

    User toEntity(JwtEntity jwtEntity);
}
