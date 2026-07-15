package com.tuan.syncSpace.Mapper;

import com.tuan.syncSpace.Dto.Request.RegisterDtoRequest;
import com.tuan.syncSpace.Dto.Request.UpdateUserDtoRequest;
import com.tuan.syncSpace.Dto.Response.UserDtoResponse;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.UserStatus;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity RegisterDtoRequestToUserEntity(RegisterDtoRequest request);
    UserDtoResponse  UserEntityToUserDtoResponse(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE
    )
    UserEntity UpdateUserDtoRequestToUserEntity(
            UpdateUserDtoRequest request,
            @MappingTarget UserEntity userEntity
    );
}
