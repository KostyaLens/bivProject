package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTDO {

    private long id;

    private LocalDate dateOfCreating;

    private LocalDate dateOfChange;

    private String FIO;

    private String email;

    private String username;

    private String password;
}
