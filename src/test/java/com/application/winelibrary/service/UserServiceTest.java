package com.application.winelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.application.winelibrary.service.user.impl.UserServiceImpl;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Verify correct UserResponseDto is returned by register() when all ok")
    void register_AllOk_ShouldReturnCorrectUserResponseDto() throws RegistrationException {
        //Given
        User user = getUserMock();

        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.USER);

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        UserRegistrationRequestDto requestDto = getUserRegistrationRequestDto();

        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.getPassword()))
                .thenReturn("$2a$10$vEjGON2nXfnHAlxWEH4bKunFzAPNNj80A2XPpQsenfF8MicVa9vH6");
        when(roleRepository.getByName(Role.RoleName.USER)).thenReturn(role);
        user.setId(1L);
        when(userRepository.save(user)).thenReturn(user);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(userMapper.toDto(user)).thenReturn(getUserResponseDtoMock());

        UserResponseDto expected = getUserResponseDtoMock();

        //When
        UserResponseDto actual = userService.register(requestDto);

        //Then
        assertEquals(expected, actual);

        verify(userMapper, times(1)).toModel(requestDto);
        verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
        verify(roleRepository, times(1)).getByName(Role.RoleName.USER);
        verify(userRepository, times(1)).save(user);
        verify(cartRepository, times(1)).save(cart);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    @DisplayName("Verify correct UserResponseDto is returned by getProfileInfo() when all ok")
    void getProfileInfo_AllOk_ShouldReturnCorrectUserResponseDto() {
        //Given
        Long id = 1L;
        User user = getUserMock();
        user.setId(id);
        UserResponseDto expected = getUserResponseDtoMock();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(getUserResponseDtoMock());

        //When
        UserResponseDto actual = userService.getProfileInfo(id);

        //Then
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(id);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    @DisplayName("Verify updateUserRole() updates user's role correctly")
    void updateUserRole_AllOk_ShouldReturnUpdatedRoleUserResponseDto() {
        //Given
        Long id = 1L;
        User user = getUserMock();
        user.setId(id);

        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName(Role.RoleName.ADMIN);

        UpdateUserRoleRequestDto requestDto = getUpdateUserRoleRequestDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(roleRepository.getByName(requestDto.role())).thenReturn(adminRole);
        user.setRoles(Set.of(adminRole));
        when(userRepository.save(user)).thenReturn(user);
        UserResponseDto expected = new UserResponseDto(
                "bob@email.com",
                "Bob",
                "Smith",
                Role.RoleName.ADMIN
        );
        when(userMapper.toDto(user)).thenReturn(expected);

        //When
        UserResponseDto actual = userService.updateUserRole(id, requestDto);

        //Then
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(id);
        verify(roleRepository, times(1)).getByName(requestDto.role());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    @DisplayName("Verify updateProfileInfo() works correctly and return proper object")
    void updateProfileInfo_AllOk_ShouldReturnCorrectlyUpdatedUserResponseDto() {
        //Given
        Long id = 1L;
        User user = getUserMock();
        user.setId(id);

        UserRegistrationRequestDto requestDto = getUserRegistrationRequestDto();
        requestDto.setLastName("Updated Name");

        UserResponseDto expected = new UserResponseDto(
                "bob@email.com",
                "Bob",
                "Updated Name",
                Role.RoleName.USER
        );

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(
                "$2a$10$vEjGON2nXfnHAlxWEH4bKunFzAPNNj80A2XPpQsenfF8MicVa9vH6"
        );
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expected);

        //When
        UserResponseDto actual = userService.updateProfileInfo(id, requestDto);

        //Then
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    private UserRegistrationRequestDto getUserRegistrationRequestDto() {
        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
        userRegistrationRequestDto.setFirstName("Bob");
        userRegistrationRequestDto.setLastName("Smith");
        userRegistrationRequestDto.setEmail("bob@email.com");
        userRegistrationRequestDto.setPassword("bob12345");
        userRegistrationRequestDto.setRepeatedPassword("1234yaroslav56");
        return userRegistrationRequestDto;
    }

    private User getUserMock() {
        User user = new User();
        user.setEmail("bob@email.com");
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setPassword("1234yaroslav56");

        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.USER);
        user.setRoles(Set.of(role));
        return user;
    }

    private UserResponseDto getUserResponseDtoMock() {
        return new UserResponseDto(
                "bob@email.com",
                "Bob",
                "Smith",
                Role.RoleName.USER
        );
    }

    private UpdateUserRoleRequestDto getUpdateUserRoleRequestDto() {
        return new UpdateUserRoleRequestDto(Role.RoleName.ADMIN);
    }
}
