package org.example.dto;

import lombok.Data;

@Data
public class JwtResponse {

    private String username;

    private String accessToken;

    private String refreshToken;
}