package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BookingSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private BookingEntity bookingEntity;
}
