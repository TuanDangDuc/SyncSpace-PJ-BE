package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Dto.Request.UpdateUserDtoRequest;
import com.tuan.syncSpace.Dto.Response.UserDtoResponse;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.UserStatus;
import com.tuan.syncSpace.Exception.AppException;
import com.tuan.syncSpace.Mapper.UserMapper;
import com.tuan.syncSpace.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserEntity findUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }


    public UserEntity findUserById(UUID userId) {
        return userRepository.findUserEntityById(userId);
    }

    @Transactional
    public UserEntity updateUserInfo(UpdateUserDtoRequest request, String username) {

        try {
            UserEntity userEntity = findUserByUsername(username);
            return userMapper.UpdateUserDtoRequestToUserEntity(request, userEntity);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to update user info: " + e.getMessage());
        }
    }

    public UserEntity deleteByUsername(String username) {
        userRepository.deleteUserEntityByUsername(username);
        return null;
    }

    public Page<UserEntity> getAllUser(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid page or size parameters");
        }

        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Transactional
    public UserEntity changeRole(String username, Role role) {
         UserEntity user = userRepository.findUserEntityByUsername(username);
         user.setRole(role);
         return user;
    }

    @Transactional
    public UserEntity changeStatus(String username, UserStatus status) {
        UserEntity user = userRepository.findUserEntityByUsername(username);
        user.setStatus(status);
        return user;
    }
}
