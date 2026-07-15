package com.tuan.syncSpace.Controller;

import com.tuan.syncSpace.Dto.Request.LoginDtoRequest;
import com.tuan.syncSpace.Dto.Request.RegisterDtoRequest;
import com.tuan.syncSpace.Dto.Request.UpdateUserDtoRequest;
import com.tuan.syncSpace.Dto.Response.LoginDtoResponse;
import com.tuan.syncSpace.Enum.Role;
import com.tuan.syncSpace.Enum.UserStatus;
import com.tuan.syncSpace.Mapper.UserMapper;
import com.tuan.syncSpace.Service.AuthService;
import com.tuan.syncSpace.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
            @RequestParam("refreshToken") UUID oldRefreshToken,
            @RequestParam("userId") UUID userId
    ) {
        LoginDtoResponse res = authService.refresh(oldRefreshToken, userId);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(res);
    }

    @GetMapping("/get-info/{username}")
    public ResponseEntity<?> getUserInfo(
            @PathVariable String username
    ) {
        var res = userMapper.UserEntityToUserDtoResponse(userService.findUserByUsername(username));
        return ResponseEntity.ok()
                .body(res);
    }

    @PutMapping("/update-info/{username}")
    public ResponseEntity<?> updateUserInfo(
            @PathVariable String username,
            @RequestBody UpdateUserDtoRequest request
    ) {
        var res = userMapper.UserEntityToUserDtoResponse(userService.updateUserInfo(request, username));
        return res != null
                ? ResponseEntity.status(HttpStatus.OK)
                    .body(res)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Update failed");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(
            @PathVariable String username
    ) {
        var res = userService.deleteByUsername(username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(res);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        var res = userService.getAllUser(page, size);

        return res != null ?
                ResponseEntity.status(HttpStatus.OK)
                    .body(res.map(userMapper::UserEntityToUserDtoResponse))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid page or size parameters");
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/change-role")
    public ResponseEntity<?> changeRole(
            @RequestParam("role") Role role,
            @RequestParam("username")  String username
    ) {
        var res = userMapper.UserEntityToUserDtoResponse(userService.changeRole(username, role));
        return res != null ?
                ResponseEntity.status(HttpStatus.OK)
                    .body(res)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Change role failed");
    }

    @PatchMapping("/change-status")
    public ResponseEntity<?> changeStatus(
            @RequestParam("status") UserStatus status,
            @RequestParam("username")  String username
    ) {
        var res = userMapper.UserEntityToUserDtoResponse(userService.changeStatus(username, status));

        return res != null ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(res)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Change status failed");
    }
}
