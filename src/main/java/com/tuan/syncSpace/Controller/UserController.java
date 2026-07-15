package com.tuan.syncSpace.Controller;

import com.tuan.syncSpace.Dto.Request.LoginDtoRequest;
import com.tuan.syncSpace.Dto.Request.RegisterDtoRequest;
import com.tuan.syncSpace.Dto.Response.LoginDtoResponse;
import com.tuan.syncSpace.Dto.Response.UserDtoResponse;
import com.tuan.syncSpace.Mapper.UserMapper;
import com.tuan.syncSpace.Service.AuthService;
import com.tuan.syncSpace.Service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final AuthService authService;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterDtoRequest request
    ) {
        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginDtoRequest request
    ) {
        var body = authService.login(request);

        return body != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(body)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @Param("refreshToken") UUID oldRefreshToken,
            @Param("userId") UUID userId
    ) {
        LoginDtoResponse res = authService.refresh(oldRefreshToken, userId);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(res);
    }

    @PostMapping("/get-info/{username}")
    public ResponseEntity<?> getUserInfo(
            @PathVariable String username
    ) {
        var userEntity = userService.findUserByUsername(username);
        return ResponseEntity.ok(
                userMapper.UserEntityToUserDtoResponse(userEntity)
        );
    }


}
