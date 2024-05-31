package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.entity.Role;
import com.application.winelibrary.entity.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    @Mapping(target = "role", source = "user.roles")
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserRegistrationRequestDto requestDto, @MappingTarget User user);

    default Role.RoleName getRole(Set<Role> roles) {
        return roles.stream()
                .findAny()
                .map(Role::getName)
                .orElseThrow(() -> new EntityNotFoundException("Can't find role"));
    }
}
