package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }


    public UserEntity findUserById(UUID userId) {
        return userRepository.findUserEntityById(userId);
    }
}
