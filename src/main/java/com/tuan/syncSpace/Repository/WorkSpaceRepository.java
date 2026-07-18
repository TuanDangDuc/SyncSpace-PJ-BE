package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpaceEntity, UUID> {
    Page<WorkSpaceEntity> findAll(Pageable pageable);
    List<WorkSpaceEntity> findByLocationEntity_Id(UUID locationId);
}
