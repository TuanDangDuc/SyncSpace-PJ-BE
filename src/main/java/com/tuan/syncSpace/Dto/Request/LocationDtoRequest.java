package com.tuan.syncSpace.Dto.Request;

import jakarta.validation.constraints.NotBlank;

public record LocationDtoRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Ward is required")
        String ward
) {
}
