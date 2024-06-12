package com.application.winelibrary.controller;

import com.application.winelibrary.dto.selection.SelectionQueryRequestDto;
import com.application.winelibrary.dto.selection.SelectionResponseDto;
import com.application.winelibrary.service.selection.SelectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Selection", description = "Endpoint for AI based wine selection")
@RestController
@Profile("!test")
@RequestMapping("/selection")
@RequiredArgsConstructor
public class SelectionController {
    private final SelectionService selectionService;

    @PostMapping
    @Operation(summary = "Send query", description = "Everyone access. Receives a user request "
            + "for wine preference. Responses with advice and list of recommended wines")
    public SelectionResponseDto sendRequest(
            @RequestBody @Valid SelectionQueryRequestDto requestDto) {
        return selectionService.sendRequest(requestDto);
    }
}
