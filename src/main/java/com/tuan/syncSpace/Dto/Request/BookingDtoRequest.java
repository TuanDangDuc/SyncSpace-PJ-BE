package com.tuan.syncSpace.Dto.Request;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookingDtoRequest(
        UUID userId,
        List<BookingSlotDtoRequest> bookingSlots
) {
}
