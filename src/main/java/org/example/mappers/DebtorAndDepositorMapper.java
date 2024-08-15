package org.example.mappers;

import org.example.entity.Account;
import org.example.entity.BankAmenities;
import org.example.entity.DebtorAndDepositor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface DebtorAndDepositorMapper {

    @Mapping(source = "account", target = "account")
    @Mapping(source = "bankAmenities", target = "paymentDeadline", qualifiedByName = "calculatePaymentDeadline")
    @Mapping(target = "id", ignore = true)
    DebtorAndDepositor toDebtor(Account account, BankAmenities bankAmenities);

    @Named("calculatePaymentDeadline")
    default LocalDateTime calculatePaymentDeadline(BankAmenities bankAmenities) {
        return LocalDateTime.now().plusMinutes(bankAmenities.getDuration());
    }
}