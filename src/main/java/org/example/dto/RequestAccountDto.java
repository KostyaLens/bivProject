package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.PinCode;

@Data
@RequiredArgsConstructor
@Schema(description = "Request AccountDto")
public class RequestAccountDto {

    @NotEmpty(message = "Не введёно username получателя")
    @Schema(name = "recipientUsername", example = "mather")
    private String recipientUsername;

    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    @Schema(name = "pinCode", example = "1111")
    private String pinCode;

    @Schema(name = "amount", example = "100500")
    @Min(value = 0, message = "Не удалось выполнить операцию, введена не коректаная сумма")
    private long amount;

    @Schema(name = "bank", example = "Сбербанк")
    @NotEmpty(message = "Не указан банк")
    private String bank;
}
