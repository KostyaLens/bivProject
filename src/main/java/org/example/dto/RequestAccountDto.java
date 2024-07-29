package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.PinCode;

@Data
@RequiredArgsConstructor
public class RequestAccountDto {
    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    private String pinCode;

    @Min(value = 0, message = "Не удалось выполнить операцию, введена не коректаная сумма")
    private long amount;
}
