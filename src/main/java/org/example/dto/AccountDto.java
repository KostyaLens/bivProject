package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validatros.PinCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private boolean isActive;

    @PinCode
    private String pinCode;

    private long balance;
}
