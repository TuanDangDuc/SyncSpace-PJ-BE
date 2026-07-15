package com.tuan.syncSpace.Dto.Response;

import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.Sex;
import com.tuan.syncSpace.Enum.UserStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UserDtoResponse(
        UUID id,
        String username,
        String email,
        Sex sex,
        Role role,
        UserStatus status,
        LocalDate dateOfBirth,
        String avatarUrl
) {
}
