package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.Password;
import org.example.validatros.UniqueEmail;
import org.example.validatros.UniqueUsername;

@Data
@RequiredArgsConstructor
@Schema(description = "User registration")
public class UserUpdateDto {

    @Schema(name = "fio", example = "Семёнов Иван Павлович")
    private String fio;

    @UniqueEmail
    @NotEmpty(message = "Введите email")
    @Schema(name = "email", example = "mail@mail.ru")
    private String email;

    @UniqueUsername
    @Schema(name = "username", example = "username")
    private String username;

    @Password
    @Schema(name = "password", example = "Passw0rd")
    private String password;
}