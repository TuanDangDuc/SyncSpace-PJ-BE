package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Dto.Request.LoginDtoRequest;
import com.tuan.syncSpace.Dto.Request.RegisterDtoRequest;
import com.tuan.syncSpace.Dto.Response.LoginDtoResponse;
import com.tuan.syncSpace.Dto.Response.UserDtoResponse;
import com.tuan.syncSpace.Entity.TokenEntity;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Entity.UserPrinciple;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.UserStatus;
import com.tuan.syncSpace.Exception.AppException;
import com.tuan.syncSpace.Mapper.UserMapper;
import com.tuan.syncSpace.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public void register(
            RegisterDtoRequest request
    ) {
        var exitUsername = userRepository.existsByUsername(request.username());
        var exitEmail = userRepository.existsByEmail(request.email());
        if (exitUsername || exitEmail) {
            var message = exitUsername && exitEmail
                    ? "Username and email already exist"
                    : exitUsername
                    ? "Username already exists"
                    : "Email already exists";
            throw AppException.builder()
                    .errorCode(HttpStatus.CONFLICT)
                    .message(message)
                    .build();
        }

        var userEntity = userMapper.RegisterDtoRequestToUserEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        userEntity.setRole(Role.USER);
        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
    }


    public LoginDtoResponse login(LoginDtoRequest request) {
        UserEntity user = userService.findUserByUsername(request.username());

        if (user == null)
            throw new AppException(HttpStatus.NOT_FOUND, "User not found");

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (AuthenticationException ex) {
            return null;
        }

        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(new UserPrinciple(user));
            UUID refreshToken = UUID.randomUUID();

            Boolean check = tokenService.exitsTokenById(user.getId());
            if  (check)
                tokenService.createNewRefreshToken(user.getId(), refreshToken);
            else
                tokenService.save(refreshToken, user.getId());

            return LoginDtoResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    public LoginDtoResponse refresh(
            UUID oldRefreshToken,
            UUID userId
    ) {
        TokenEntity tokenEntity = tokenService.getRefreshTokenByUserId(userId);

        if (oldRefreshToken.equals(tokenEntity.getRefreshToken())) {
            UserEntity userEntity = userService.findUserById(userId);
            String token = jwtService.generateToken(new UserPrinciple(userEntity));
            UUID newRefreshToken = UUID.randomUUID();

            tokenService.createNewRefreshToken(userId, newRefreshToken);

            return LoginDtoResponse.builder()
                    .accessToken(token)
                    .refreshToken(newRefreshToken)
                    .build();
        } else {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
    }
}
