package com.application.winelibrary.dto.user.management;

public record GoogleUserDto(
        String id,
        String email,
        String given_name,
        String family_name
) {
}
