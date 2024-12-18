package org.example.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UserBankServiceDto {

    private String username;

    private long amount;

    private LocalDateTime deadline;

    private String bank;
}
