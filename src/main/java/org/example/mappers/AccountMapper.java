package org.example.mappers;

import org.example.dto.AccountDto;
import org.example.dto.CreateAccountDto;
import org.example.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "bank.name", target = "bank")
    AccountDto toDto(Account account);

    @Mapping(target = "bank", ignore = true)
    Account toEntity(CreateAccountDto createAccountDto);
}
