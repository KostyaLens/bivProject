package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.PinCode;

@Data
@RequiredArgsConstructor
@Schema(description = "Create account")
public class CreateAccountDto {

    @PinCode
    @NotEmpty(message = "Не введён пин-код")
    @Schema(name = "pinCode", example = "1111")
    private String pinCode;

    @Schema(name = "socialMediaStatus", example = "My little pony is cool")
    private String socialMediaStatus;

    @Schema(name = "bank", example = "Т-Банк")
    @NotEmpty(message = "Не указан банк")
    private String bank;

}
