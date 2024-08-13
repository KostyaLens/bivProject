package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "login")
public class JwtRequest {

    @Schema(name = "username", example = "username")
    @NotEmpty(message = "Не введено поле username")
    private String username;

    @Schema(name = "password", example = "Passw0rd")
    @NotEmpty(message = "Не введён пароль")
    private String password;
}
