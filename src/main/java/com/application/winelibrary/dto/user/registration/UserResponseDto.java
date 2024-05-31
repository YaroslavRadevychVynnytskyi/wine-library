package com.application.winelibrary.dto.user.registration;

import com.application.winelibrary.entity.Role;

public record UserResponseDto(
        String email,
        String firstName,
        String lastName,
        Role.RoleName role
) {
}
