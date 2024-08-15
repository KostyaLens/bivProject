package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class UserBankServiceDto {

    private String username;

    private long amount;

    private LocalDateTime deadline;
}
