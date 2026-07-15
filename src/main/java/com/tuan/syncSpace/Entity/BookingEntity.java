package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private WorkSpaceEntity workSpace;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private UserEntity user;

    @OneToMany(
            mappedBy = "bookingEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<BookingSlotEntity> bookingSlots;
}
