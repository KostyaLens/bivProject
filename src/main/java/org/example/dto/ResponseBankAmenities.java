package org.example.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseBankAmenities {

    private long id;

    private String name;

    private String percent;

    private int duration;

    private int serviceNumber;
}
