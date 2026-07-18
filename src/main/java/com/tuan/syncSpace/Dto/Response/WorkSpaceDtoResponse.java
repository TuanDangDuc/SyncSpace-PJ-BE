package com.tuan.syncSpace.Dto.Response;

import com.tuan.syncSpace.Enum.Type;
import com.tuan.syncSpace.Enum.WorkSpaceStatus;
import java.util.UUID;

public record WorkSpaceDtoResponse(
        UUID id,
        Integer floor,
        String roomNumber,
        Type type,
        Integer acreage,
        WorkSpaceStatus status,
        Integer capacity,
        UUID locationId
) {
}
