package com.application.winelibrary.dto.user.management;

import com.application.winelibrary.entity.Role;

public record UpdateUserRoleRequestDto(
        Role.RoleName role
) {
}
