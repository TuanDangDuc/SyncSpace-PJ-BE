package com.tuan.syncSpace.Repository;

import com.tuan.syncSpace.Entity.TokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

    @Query("update TokenEntity t set " +
            "t.refreshToken = ?2, " +
            "t.expireTime = ?3 " +
            "where t.user.id = ?1")
    @Modifying
    @Transactional
    void replaceOldRefreshToken(UUID userId, UUID refreshToken, LocalDate newExpired);

    Boolean existsTokenEntityByUser_Id(UUID userId);

    TokenEntity findTokenEntityByUser_Id(UUID userId);
}
