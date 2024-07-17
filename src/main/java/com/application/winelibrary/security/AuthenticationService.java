package com.application.winelibrary.security;

import com.application.winelibrary.dto.user.login.OAuthLoginUserRequestDto;
import com.application.winelibrary.dto.user.login.UserLoginRequestDto;
import com.application.winelibrary.dto.user.login.UserLoginResponseDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final GoogleUserService googleUserService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        String token = jwtUtil.generateToken(authenticate.getName());
        return new UserLoginResponseDto(token);
    }

    public UserLoginResponseDto authenticateViaOAuth(OAuthLoginUserRequestDto requestDto) {
        User user = googleUserService.retrieveUserData(requestDto.googleClientIdToken());
        userService.processOAuthLogin(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new UserLoginResponseDto(token);
    }
}
