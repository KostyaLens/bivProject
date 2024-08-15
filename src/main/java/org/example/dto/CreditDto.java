package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.example.validatros.PinCode;

public class CreditDto {
    @NotEmpty(message = "Не указан банк")
    private String nameBank;

    @Min(value = 0, message = "нельзя брать в кредит отрицательные числа")
    private long amount;

    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    @Schema(name = "pinCode", example = "1111")
    private String pinCode;

    @Min(value = 0, message = "Нет такого варинта")
    private int typeOfCredit;
}
