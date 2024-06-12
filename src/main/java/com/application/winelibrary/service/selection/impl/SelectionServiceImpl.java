package com.application.winelibrary.service.selection.impl;

import com.application.winelibrary.dto.selection.SelectionQueryRequestDto;
import com.application.winelibrary.dto.selection.SelectionResponseDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.exception.InvalidJpqlException;
import com.application.winelibrary.mapper.WineMapper;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.ai.AiService;
import com.application.winelibrary.service.selection.SelectionService;
import com.application.winelibrary.util.ResourceReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class SelectionServiceImpl implements SelectionService {
    private static final String BAD_REQUEST = "We are unable to find wines "
            + "based on your message. Please modify you request and try again";

    @Value("classpath:default-ai-query.txt")
    private Resource resource;

    private final AiService aiService;
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    @Override
    public SelectionResponseDto sendRequest(SelectionQueryRequestDto requestDto) {
        String defaultAiQuery = ResourceReader.asString(resource);

        CompletableFuture<String> jpqlFuture = CompletableFuture.supplyAsync(() ->
                aiService.sendRequest(defaultAiQuery + requestDto.userQuery()));
        CompletableFuture<String> adviceFuture = CompletableFuture.supplyAsync(() ->
                aiService.sendRequest(requestDto.userQuery()));

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(jpqlFuture, adviceFuture);

        String jpql;
        String advice;

        try {
            allFutures.get();
            jpql = jpqlFuture.get();
            advice = adviceFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Can't retrieve futures", e);
        }

        try {
            List<Wine> wines = wineRepository.findAllByJpqlQuery(jpql, Wine.class);

            List<WineDetailedResponseDto> wineDtoList = wines.stream()
                    .map(wineMapper::toDto)
                    .toList();
            return new SelectionResponseDto(advice, wineDtoList);
        } catch (InvalidJpqlException e) {
            return new SelectionResponseDto(BAD_REQUEST, Collections.emptyList());
        }
    }
}
