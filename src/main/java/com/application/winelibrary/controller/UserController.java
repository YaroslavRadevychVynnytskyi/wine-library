package com.application.winelibrary.controller;

import com.application.winelibrary.dto.user.management.UpdatePasswordRequestDto;
import com.application.winelibrary.dto.user.management.UpdatePasswordResponseDto;
import com.application.winelibrary.dto.user.management.UpdateUserRoleRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    @Operation(summary = "Retrieve user's profile info",
            description = "User access. Retrieves user's data")
    public UserResponseDto getProfileInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.getProfileInfo(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/me")
    @Operation(summary = "Update user's profile info",
            description = "User access. Updated user's data")
    public UserResponseDto updateProfileInfo(
            Authentication authentication,
            @RequestBody @Valid UserRegistrationRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return userService.updateProfileInfo(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/role")
    @Operation(summary = "Update user's role",
            description = "Admin access. Updates user's role")
    public UserResponseDto updateUserRole(
            @RequestBody UpdateUserRoleRequestDto requestDto,
            @PathVariable Long id
    ) {
        return userService.updateUserRole(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update-password")
    @Operation(summary = "Update password", description = "User access. Updates user's password")
    public ResponseEntity<UpdatePasswordResponseDto> updateUserPassword(
            Authentication authentication,
            @RequestBody @Valid UpdatePasswordRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        UpdatePasswordResponseDto responseDto = userService
                .updateUserPassword(user.getId(), requestDto);
        if (responseDto.isSuccess()) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
