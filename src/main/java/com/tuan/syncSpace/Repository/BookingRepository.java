package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    @EntityGraph(attributePaths = {"bookingSlots",  "bookingSlots.workSpaceEntity"})
    Optional<BookingEntity> findById(UUID id);

    @Query("select b from BookingEntity b where b.user.id = ?1")
    Page<BookingEntity> findByUserId(UUID userId, Pageable pageable);
}
