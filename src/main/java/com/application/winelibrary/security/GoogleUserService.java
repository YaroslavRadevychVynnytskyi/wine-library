package com.application.winelibrary.security;

import com.application.winelibrary.dto.user.management.GoogleUserDto;
import com.application.winelibrary.entity.Role;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.repository.role.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class GoogleUserService {
    @Value("${oauth2.userinfo.url}")
    private String userInfoUrl;

    private final ObjectMapper objectMapper;
    private final OkHttpClient client;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random;

    public User retrieveUserData(String googleClientIdToken) {
        Request request = new Request.Builder()
                .url(userInfoUrl)
                .addHeader("Authorization", "Bearer " + googleClientIdToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            GoogleUserDto googleUserDto = objectMapper.readValue(responseBody, GoogleUserDto.class);

            return User.builder()
                    .oauthProvider("google")
                    .oauthId(googleUserDto.id())
                    .firstName(googleUserDto.given_name())
                    .lastName(googleUserDto.family_name())
                    .email(googleUserDto.email())
                    .password(passwordEncoder.encode(String.valueOf(100000
                            + random.nextInt(900000))))
                    .roles(Set.of(roleRepository.getByName(Role.RoleName.USER)))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Can't send a request", e);
        }
    }
}
