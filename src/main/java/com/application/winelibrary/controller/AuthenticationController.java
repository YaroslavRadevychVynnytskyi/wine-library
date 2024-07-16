package com.application.winelibrary.controller;

import com.application.winelibrary.dto.user.login.OAuthLoginUserRequestDto;
import com.application.winelibrary.dto.user.login.UserLoginRequestDto;
import com.application.winelibrary.dto.user.login.UserLoginResponseDto;
import com.application.winelibrary.dto.user.registration.UserRegistrationRequestDto;
import com.application.winelibrary.dto.user.registration.UserResponseDto;
import com.application.winelibrary.exception.RegistrationException;
import com.application.winelibrary.security.AuthenticationService;
import com.application.winelibrary.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoints for authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Profile("!test")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates and saves a new user to DB")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sing in for user", description = "Sign in endpoint. Returns JWT")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/oauth/sign-in")
    @Operation(summary = "OAuth sing in for user",
            description = "OAuth sign in endpoint. Returns JWT")
    public ResponseEntity<UserLoginResponseDto> oauthLogin(
            @RequestBody OAuthLoginUserRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.authenticateViaOAuth(requestDto));
    }
}
