package org.example.mappers;

import org.example.dto.AccountDTO;
import org.example.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDto(Account account);

    Account toEntity(AccountDTO accountDTO);
}
