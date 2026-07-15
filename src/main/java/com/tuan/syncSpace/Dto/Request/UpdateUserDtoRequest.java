package com.tuan.syncSpace.Dto.Request;

import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.Sex;
import com.tuan.syncSpace.Enum.UserStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserDtoRequest(
        String email,
        Sex sex,
        LocalDate dateOfBirth,
        String avatarUrl
) {
}
