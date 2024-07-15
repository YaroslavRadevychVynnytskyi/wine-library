package com.application.winelibrary.service.user.impl;

import com.application.winelibrary.dto.user.management.UpdatePasswordRequestDto;
import com.application.winelibrary.dto.user.management.UpdatePasswordResponseDto;
import com.application.winelibrary.dto.user.management.UpdateUserFirstNameRequestDto;
import com.application.winelibrary.dto.user.management.UpdateUserLastNameRequestDto;
import com.application.winelibrary.dto.user.management.UpdateUserRoleRequestDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.entity.Role;
import com.application.winelibrary.entity.ShoppingCart;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.exception.RegistrationException;
import com.application.winelibrary.mapper.UserMapper;
import com.application.winelibrary.repository.cart.ShoppingCartRepository;
import com.application.winelibrary.repository.role.RoleRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        checkUserExistence(requestDto.getEmail());
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(roleRepository.getByName(Role.RoleName.USER)));
        User savedUser = userRepository.save(user);
        createShoppingCart(savedUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto getProfileInfo(Long userId) {
        User user = getUserById(userId);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUserRole(Long userId, UpdateUserRoleRequestDto requestDto) {
        User user = getUserById(userId);
        user.setRoles(Set.of(roleRepository.getByName(requestDto.role())));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto processOAuthPostLogin(String oauthId,
                                                 String username,
                                                 String firstName,
                                                 String lastName) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            User newUser = User.builder()
                    .oauthProvider("google")
                    .oauthId(oauthId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(username)
                    .password(passwordEncoder.encode(""))
                    .roles(Set.of(roleRepository.getByName(Role.RoleName.USER)))
                    .build();

            User savedUser = userRepository.save(newUser);
            createShoppingCart(savedUser);
            return userMapper.toDto(savedUser);
        }
        return userMapper.toDto(user.get());
    }

    @Override
    public UpdatePasswordResponseDto updateUserPassword(Long userId,
                                                        UpdatePasswordRequestDto requestDto) {
        User user = getUserById(userId);

        if (passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())
                && !passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            userRepository.save(user);
            return new UpdatePasswordResponseDto("Successfully updated password", true);
        }
        return new UpdatePasswordResponseDto("Failed to update password", false);
    }

    @Override
    public UserResponseDto updateUserFirstName(Long userId,
                                               UpdateUserFirstNameRequestDto requestDto) {
        User user = getUserById(userId);
        user.setFirstName(requestDto.firstName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateUserLastName(Long userId,
                                              UpdateUserLastNameRequestDto requestDto) {
        User user = getUserById(userId);
        user.setLastName(requestDto.lastName());
        return userMapper.toDto(userRepository.save(user));
    }

    private void checkUserExistence(String email) throws RegistrationException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " already exists!");
        }
    }

    private void createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Can't find user with ID: " + userId));
    }
}
