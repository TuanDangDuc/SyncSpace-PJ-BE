package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {
    Page<LocationEntity> findAll(Pageable pageable);
    LocationEntity findLocationEntityByName(String name);

    LocationEntity findLocationEntityById(UUID id);

    void deleteLocationEntityById(UUID id);
}
