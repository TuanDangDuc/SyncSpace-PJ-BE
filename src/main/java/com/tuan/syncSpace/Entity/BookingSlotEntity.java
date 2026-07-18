package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private Integer cost;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private BookingEntity bookingEntity;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"bookingSlotEntities"})
    private WorkSpaceEntity workSpaceEntity;
}
