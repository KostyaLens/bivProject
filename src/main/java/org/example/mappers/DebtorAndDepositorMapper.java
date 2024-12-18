package org.example.mappers;

import org.example.dto.UserBankServiceDto;
import org.example.entity.Account;
import org.example.entity.BankAmenities;
import org.example.entity.DebtorAndDepositor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DebtorAndDepositorMapper {

    @Mapping(source = "account", target = "account")
    @Mapping(source = "bankAmenities", target = "paymentDeadline", qualifiedByName = "calculatePaymentDeadline")
    @Mapping(target = "id", ignore = true)
    DebtorAndDepositor toDebtor(Account account, BankAmenities bankAmenities);

    @Mapping(target = "bank", ignore = true)
    @Mapping(source = "totalDebt", target = "amount")
    @Mapping(source = "paymentDeadline", target = "deadline")
    @Mapping(target = "username", ignore = true)
    List<UserBankServiceDto> toUserBankServiceDto(List<DebtorAndDepositor> debtorAndDepositorList);

    @Named("calculatePaymentDeadline")
    default LocalDateTime calculatePaymentDeadline(BankAmenities bankAmenities) {
        return LocalDateTime.now().plusMinutes(bankAmenities.getDuration());
    }

}