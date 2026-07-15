package com.tuan.syncSpace.Dto.Response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LoginDtoResponse(
        String accessToken,
        UUID refreshToken
) {
}
