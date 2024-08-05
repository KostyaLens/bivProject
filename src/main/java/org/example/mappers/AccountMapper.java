package org.example.mappers;

import org.example.dto.AccountDto;
import org.example.dto.CreateAccountDto;
import org.example.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDTO);

    @Mapping(target = "bank", ignore = true)
    Account toEntity(CreateAccountDto createAccountDto);
}
