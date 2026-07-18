package com.tuan.syncSpace.Dto.Response;

import java.util.UUID;

public record LocationDtoResponse(
        UUID id,
        String name,
        String ward
) {
}
