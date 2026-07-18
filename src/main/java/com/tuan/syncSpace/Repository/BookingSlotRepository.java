package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.BookingSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlotEntity, UUID> {

    @Query("select count(b) " +
            "from BookingSlotEntity b " +
            "where ?1 <= b.endTime and ?2 >= b.startTime")
    int validBooking(LocalDateTime startTime, LocalDateTime endTime);
}
