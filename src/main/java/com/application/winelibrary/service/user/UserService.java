package com.application.winelibrary.service.user;

import com.application.winelibrary.dto.user.management.UpdatePasswordRequestDto;
import com.application.winelibrary.dto.user.management.UpdatePasswordResponseDto;
import com.application.winelibrary.dto.user.management.UpdateUserFirstNameRequestDto;
import com.application.winelibrary.dto.user.management.UpdateUserLastNameRequestDto;
import com.application.winelibrary.dto.user.management.UpdateUserRoleRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto getProfileInfo(Long userId);

    UserResponseDto updateUserRole(Long userId, UpdateUserRoleRequestDto requestDto);

    UserResponseDto processOAuthLogin(User user);

    UpdatePasswordResponseDto updateUserPassword(Long userId, UpdatePasswordRequestDto requestDto);

    UserResponseDto updateUserFirstName(Long userId, UpdateUserFirstNameRequestDto requestDto);

    UserResponseDto updateUserLastName(Long userId, UpdateUserLastNameRequestDto requestDto);
}
