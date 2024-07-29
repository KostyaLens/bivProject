package org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.PinCode;
@Data
@RequiredArgsConstructor
public class CreateAccountDto {
    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    private String pinCode;
    private String socialMediaStatus;

}
