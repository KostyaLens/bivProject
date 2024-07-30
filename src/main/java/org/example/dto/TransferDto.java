package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.PinCode;

@Data
@RequiredArgsConstructor
@Schema(description = "transfer")
public class TransferDto {
    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    @Schema(name = "pin-code", example = "1111")
    private String pinCode;
    @Min(value = 0, message = "Не удалось выполнить операцию, введена не коректаная сумма")
    @Schema(name = "amount", example = "100500")
    private long amount;
    @NotEmpty(message = "Не введёно username получателя")
    @Schema(name = "recipient username", example = "mather")
    private String recipientUsername;
}
