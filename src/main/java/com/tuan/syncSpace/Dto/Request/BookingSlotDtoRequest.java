package com.tuan.syncSpace.Dto.Request;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingSlotDtoRequest(
        UUID workspaceId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
