package com.application.winelibrary.service.ai.impl;

import com.application.winelibrary.service.ai.AiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class DeepSeekService implements AiService {
    private static final String DEEPSEEK_CHAT = "deepseek-chat";
    private static final String DEEPSEEK_CODER = "deepseek-coder";
    private static final int DEFAULT_REQUEST_MIN_LENGTH = 1370;

    private final ObjectMapper objectMapper;
    private final OkHttpClient client;

    @Value("${deepseek.url}")
    private String deepSeekUrl;
    @Value("${deepseek.key}")
    private String deepSeekKey;

    @Override
    public String sendRequest(String requestMessage) {
        String chatModel;
        if (requestMessage.length() > DEFAULT_REQUEST_MIN_LENGTH) {
            chatModel = DEEPSEEK_CODER;
        } else {
            chatModel = DEEPSEEK_CHAT;
        }

        String jsonPayload = getPayload(requestMessage, chatModel);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonPayload);
        Request request = new Request.Builder()
                .url(deepSeekUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + deepSeekKey)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("Can not execute new call", e);
        }
        return parseResponse(response);
    }

    @NotNull
    private String getPayload(String requestMessage, String chatModel) {
        String formattedRequestMessage = requestMessage.replaceAll("\\p{Cntrl}", "");

        return "{\n"
                + "  \"messages\": [\n"
                + "    {\n"
                + "      \"content\": \"You are a helpful assistant\",\n"
                + "      \"role\": \"system\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"content\": \"" + formattedRequestMessage + "\",\n"
                + "      \"role\": \"user\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"model\": \"" + chatModel + "\",\n"
                + "  \"frequency_penalty\": 0,\n"
                + "  \"max_tokens\": 128,\n"
                + "  \"presence_penalty\": 0,\n"
                + "  \"stop\": null,\n"
                + "  \"stream\": false,\n"
                + "  \"temperature\": 1,\n"
                + "  \"top_p\": 1,\n"
                + "  \"logprobs\": false,\n"
                + "  \"top_logprobs\": null\n"
                + "}";
    }

    private String parseResponse(Response response) {
        String responseString = null;
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("Can not retrieve response string", e);
        }
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(responseString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can not read tree", e);
        }
        JsonNode choicesNode = rootNode.path("choices");
        JsonNode firstChoiceNode = choicesNode.get(0);
        JsonNode messageNode = firstChoiceNode.path("message");
        return messageNode.path("content").asText();
    }
}
