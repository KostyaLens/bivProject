package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Account blocking")
public class BlockAccountDto {
    @Schema(name = "username of the account being blocked", example = "petrovich")
    private String username;
}
