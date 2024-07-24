package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.validatros.Password;

@Data
@RequiredArgsConstructor
public class UserCreationDto {

    @NotEmpty(message = "Заполниете поле ФИО")
    private String fio;

    @Email(message = "Такого email не существуе")
    private String email;

    @NotEmpty(message = "Введите username")
    private String username;

    @Password
    private String password;

    private AccountDto accountDTO;
}
