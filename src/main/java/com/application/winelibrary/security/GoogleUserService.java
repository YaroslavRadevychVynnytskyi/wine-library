package com.application.winelibrary.security;

import com.application.winelibrary.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class GoogleUserService {
    private final ObjectMapper objectMapper;
    private final OkHttpClient client;
    @Value("${oauth2.userinfo.url}")
    private String userInfoUrl;

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
            return objectMapper.readValue(responseBody, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
