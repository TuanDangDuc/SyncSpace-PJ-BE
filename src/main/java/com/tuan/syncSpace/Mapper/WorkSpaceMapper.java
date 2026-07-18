package com.tuan.syncSpace.Mapper;

import com.tuan.syncSpace.Dto.Request.WorkSpaceDtoRequest;
import com.tuan.syncSpace.Dto.Response.WorkSpaceDtoResponse;
import com.tuan.syncSpace.Entity.WorkSpaceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkSpaceMapper {
    @Mapping(target = "locationId", source = "locationEntity.id")
    WorkSpaceDtoResponse WorkSpaceEntityToWorkSpaceDtoResponse(WorkSpaceEntity entity);

    @Mapping(target= "locationEntity.id", source = "locationId")
    WorkSpaceEntity WorkSpaceDtoRequestToEntity(WorkSpaceDtoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy =
            org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    WorkSpaceEntity UpdateWorkSpaceDtoRequestToEntity(WorkSpaceDtoRequest request, @MappingTarget WorkSpaceEntity entity);

}
