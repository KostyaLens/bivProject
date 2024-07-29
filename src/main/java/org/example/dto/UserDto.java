package org.example.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String fio;

    private String email;

    private String username;

}
