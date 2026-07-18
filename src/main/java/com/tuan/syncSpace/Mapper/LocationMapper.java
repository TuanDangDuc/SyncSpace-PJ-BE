package com.tuan.syncSpace.Mapper;

import com.tuan.syncSpace.Dto.Request.LocationDtoRequest;
import com.tuan.syncSpace.Dto.Response.LocationDtoResponse;
import com.tuan.syncSpace.Entity.LocationEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationEntity LocationDtoRequestToLocationEntity(LocationDtoRequest request);
    LocationDtoResponse LocationEntityToLocationDtoResponse(LocationEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy =
            org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
    )
    LocationEntity UpdateLocationDtoRequestToLocationEntity(LocationDtoRequest request, @MappingTarget LocationEntity location);
}
