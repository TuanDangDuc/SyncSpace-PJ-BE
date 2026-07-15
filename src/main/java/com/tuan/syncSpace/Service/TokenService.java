package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Entity.TokenEntity;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void save(UUID refreshToken, UUID id) {
        TokenEntity tokenEntity = TokenEntity.builder()
                .refreshToken(refreshToken)
                .user(
                        UserEntity.builder()
                                .id(id)
                                .build()
                )
                .expireTime(LocalDate.now().plusDays(30))
                .build();
        tokenRepository.save(tokenEntity);
    }

    public Boolean exitsTokenById(UUID userId) {
        return tokenRepository.existsTokenEntityByUser_Id((userId));
    }

    public void createNewRefreshToken(UUID userId, UUID refreshToken) {
        LocalDate newExpired = LocalDate.now().plusDays(30);
        tokenRepository.replaceOldRefreshToken(userId, refreshToken, newExpired);
    }

    public TokenEntity getRefreshTokenByUserId(UUID userId) {
        return tokenRepository.findTokenEntityByUser_Id(userId);
    }
}
