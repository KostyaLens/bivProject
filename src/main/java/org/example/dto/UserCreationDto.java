package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.Password;
import org.example.validatros.UniqueEmail;
import org.example.validatros.UniqueUsername;

@Data
@RequiredArgsConstructor
public class UserCreationDto {

    @NotEmpty(message = "Заполниете поле ФИО")
    private String fio;

    @Email(message = "Такого email не существуе")
    @UniqueEmail
    @NotEmpty(message = "Введите email")
    private String email;

    @NotEmpty(message = "Введите username")
    @UniqueUsername
    private String username;

    @Password
    @NotEmpty(message = "Введите password")
    private String password;
}
