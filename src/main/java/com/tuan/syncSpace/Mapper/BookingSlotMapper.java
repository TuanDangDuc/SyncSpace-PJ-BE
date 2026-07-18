package com.tuan.syncSpace.Mapper;

import com.tuan.syncSpace.Dto.Request.BookingSlotDtoRequest;
import com.tuan.syncSpace.Entity.BookingEntity;
import com.tuan.syncSpace.Entity.BookingSlotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingSlotMapper {

    @Mapping(target = "workSpaceEntity.id", source = "workspaceId")
    @Mapping(target = "bookingStatus", constant = "PENDING")
    public BookingSlotEntity BookingSlotDtoRequestToEntity(BookingSlotDtoRequest bookingSlotDtoRequest);
}
