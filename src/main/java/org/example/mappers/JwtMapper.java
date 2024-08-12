package org.example.mappers;

import org.example.entity.User;
import org.example.security.UserDetailsImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {

    UserDetailsImpl toJwt(User user);

    User toEntity(UserDetailsImpl jwtEntity);
}
