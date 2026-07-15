package com.tuan.syncSpace.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.Sex;
import com.tuan.syncSpace.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @CreatedDate
    private LocalDate dateOfBirth;
    @Column(columnDefinition = "TEXT")
    private String avatarUrl;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<BookingEntity> bookings;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private TokenEntity token;


}
