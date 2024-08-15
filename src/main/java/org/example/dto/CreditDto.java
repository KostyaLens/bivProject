package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.validatros.PinCode;

@Getter
@Setter
@RequiredArgsConstructor
@Schema(description = "creditDto")
public class CreditDto {

    @Schema(name = "nameBank", example = "Т-Банк")
    @NotEmpty(message = "Не указан банк")
    private String nameBank;

    @Min(value = 0, message = "нельзя брать в кредит отрицательные числа")
    @Schema(name = "amount", example = "12")
    private long amount;

    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    @Schema(name = "pinCode", example = "1111")
    private String pinCode;

    @Schema(name = "serviceNumber", example = "2")
    @Min(value = 0, message = "Нет такого варинта")
    private int serviceNumber;
}
