package com.application.winelibrary.service.user.impl;

import com.application.winelibrary.dto.user.management.UpdateUserRoleRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.entity.Role;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.exception.RegistrationException;
import com.application.winelibrary.mapper.UserMapper;
import com.application.winelibrary.repository.role.RoleRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        checkUserExistence(requestDto.getEmail());
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(roleRepository.getByName(Role.RoleName.USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto getProfileInfo(Long userId) {
        User user = getUserById(userId);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateProfileInfo(Long userId, UserRegistrationRequestDto requestDto) {
        User user = getUserById(userId);
        userMapper.updateUserFromDto(requestDto, user);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUserRole(Long userId, UpdateUserRoleRequestDto requestDto) {
        User user = getUserById(userId);
        user.setRoles(Set.of(roleRepository.getByName(requestDto.role())));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkUserExistence(String email) throws RegistrationException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " already exists!");
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Can't find user with ID: " + userId));
    }
}
