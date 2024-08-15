package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Account blocking")
public class BlockAccountDto {

    @Schema(name = "username", example = "petrovich")
    @NotEmpty
    private String username;

    @Schema(name = "bank", example = "Сбербанк")
    @NotEmpty(message = "Не указан банк")
    private String bank;
}
