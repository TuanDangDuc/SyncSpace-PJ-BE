package com.tuan.syncSpace.Dto.Request;

import jakarta.validation.constraints.NotBlank;

public record LoginDtoRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
