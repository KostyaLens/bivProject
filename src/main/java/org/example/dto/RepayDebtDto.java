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
@Schema(description = "ServiceBankDto")
public class RepayDebtDto {

    @Schema(name = "nameBank", example = "Сбербанк")
    @NotEmpty(message = "Не введенно название банка")
    private String nameBank;

    @Schema(name = "amount", example = "100")
    @Min(value = 0, message = "Не возможно вернуть такую сумму")
    private long amount;

    @Schema(name = "pinCode", example = "1111")
    @NotEmpty(message = "Введите пин-код")
    @PinCode
    private String pinCode;

}
