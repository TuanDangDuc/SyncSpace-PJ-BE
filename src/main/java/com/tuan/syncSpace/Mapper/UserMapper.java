package com.tuan.syncSpace.Mapper;

import com.tuan.syncSpace.Dto.Request.RegisterDtoRequest;
import com.tuan.syncSpace.Dto.Response.UserDtoResponse;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity RegisterDtoRequestToUserEntity(RegisterDtoRequest request);
    UserDtoResponse  UserEntityToUserDtoResponse(UserEntity userEntity);
}
