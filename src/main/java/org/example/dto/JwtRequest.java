package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "login")
public class JwtRequest {

    @Schema(name = "username", example = "username")
    private String username;

    @Schema(name = "password", example = "Passw0rd")
    private String password;
}
