package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validatros.Password;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTDO {

    private long id;

    private LocalDate dateOfCreating = LocalDate.now();

    private LocalDate dateOfChange = LocalDate.now();

//    @NotEmpty(message = "Заполниете поле ФИО")
    private String FIO;

    @Email(message = "Такого email не существуе")
    private String email;

    @NotEmpty(message = "Введите username")
    private String username;

    @Password
    private String password;

    private AccountDTO accountDTO;
}
