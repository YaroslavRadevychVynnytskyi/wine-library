package com.application.winelibrary.service.user;

import com.application.winelibrary.dto.user.management.UpdatePasswordRequestDto;
import com.application.winelibrary.dto.user.management.UpdatePasswordResponseDto;
import com.application.winelibrary.dto.user.management.UpdateUserRoleRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto getProfileInfo(Long userId);

    UserResponseDto updateProfileInfo(Long userId, UserRegistrationRequestDto requestDto);

    UserResponseDto updateUserRole(Long userId, UpdateUserRoleRequestDto requestDto);

    UserResponseDto processOAuthPostLogin(String oauthId,
                                          String username,
                                          String firstName,
                                          String lastName);

    UpdatePasswordResponseDto updateUserPassword(Long userId, UpdatePasswordRequestDto requestDto);
}
