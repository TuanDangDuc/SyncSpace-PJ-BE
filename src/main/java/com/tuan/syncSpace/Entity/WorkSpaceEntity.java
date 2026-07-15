package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.Type;
import com.tuan.syncSpace.Enum.WorkSpaceStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class WorkSpaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer floor;
    private Integer roomNumber;
    private Type type;
    private Integer acreage;
    private WorkSpaceStatus status;
    private Integer capacity;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private LocationEntity locationEntity;

    @OneToMany(
            mappedBy = "workSpace"
    )
    @JsonManagedReference
    private List<BookingEntity> bookingEntities;
}
