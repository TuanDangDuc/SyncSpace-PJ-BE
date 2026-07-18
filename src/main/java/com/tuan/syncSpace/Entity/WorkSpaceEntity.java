package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.Type;
import com.tuan.syncSpace.Enum.WorkSpaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer floor;
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer acreage;
    @Enumerated(EnumType.STRING)
    private WorkSpaceStatus status;
    private Integer capacity;
    private Integer pricePerHour;
    private String thumbnailUrl;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private LocationEntity locationEntity;

    @OneToMany(
            mappedBy = "workSpaceEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties({"workSpaceEntity"})
    private List<BookingSlotEntity>  bookingSlotEntities;

}
