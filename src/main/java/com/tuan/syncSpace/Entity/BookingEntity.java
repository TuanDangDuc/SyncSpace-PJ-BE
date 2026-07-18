package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.BookingStatus;
import com.tuan.syncSpace.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private LocalDateTime createAt;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Integer totalCost;

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
