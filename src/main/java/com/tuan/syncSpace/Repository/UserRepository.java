package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.UserEntity;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserEntity findUserEntityByUsername(String username);

    UserEntity findUserEntityById(UUID id);
}
