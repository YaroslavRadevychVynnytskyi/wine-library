package com.application.winelibrary.security;

import com.application.winelibrary.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOAuth2User oathUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oathUser.getAttribute("email");

        userService.processOAuthPostLogin(
                oathUser.getAttribute("sub"),
                email,
                oathUser.getAttribute("given_name"),
                oathUser.getAttribute("family_name")
        );

        String jwt = jwtUtil.generateToken(email);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        response.getWriter().flush();
    }
}
