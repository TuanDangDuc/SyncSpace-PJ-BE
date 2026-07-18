package com.tuan.syncSpace.Dto.Request;

import com.tuan.syncSpace.Enum.Type;
import com.tuan.syncSpace.Enum.WorkSpaceStatus;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record WorkSpaceDtoRequest(
        @NotNull(message = "Floor is required")
        Integer floor,
        @NotNull(message = "Room number is required")
        String roomNumber,
        @NotNull(message = "Type is required")
        Type type,
        @NotNull(message = "Acreage is required")
        Integer acreage,
        @NotNull(message = "Status is required")
        WorkSpaceStatus status,
        @NotNull(message = "Capacity is required")
        Integer capacity,
        @NotNull(message = "Location ID is required")
        UUID locationId,
        @NotNull(message = "Thumbnail Image is required")
        String thumbnailUrl
) {
}
