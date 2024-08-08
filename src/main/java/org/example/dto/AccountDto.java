package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Bank;
import org.example.validatros.PinCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private boolean isActive;

    private long balance;

    private String socialMediaStatus;

    private Bank bank;
}
