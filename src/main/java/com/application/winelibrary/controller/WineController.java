package com.application.winelibrary.controller;

import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.service.wine.WineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wine management", description = "Endpoints for managing wine")
@RestController
@RequestMapping("/wines")
@RequiredArgsConstructor
public class WineController {
    private final WineService wineService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Add a new wine", description = "Saves new wine into db")
    public WineDetailedResponseDto save(@RequestBody @Valid CreateWineRequestDto requestDto) {
        return wineService.save(requestDto);
    }

    @GetMapping
    @Operation(
            summary = "Get a list of wines",
            description = "Provides a list of all wines with pagination available"
    )
    public List<WineDetailedResponseDto> findAll(Pageable pageable) {
        return wineService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get wine's detailed information",
            description = "Retrieves wine's detailed information based of wine's ID"
    )
    public WineDetailedResponseDto findById(@PathVariable Long id) {
        return wineService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update wine", description = "Updates a wine by ID")
    public WineDetailedResponseDto updateById(
            @PathVariable Long id,
            @RequestBody @Valid CreateWineRequestDto requestDto
    ) {
        return wineService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove wine", description = "Removes a wine from db by ID")
    public void deleteById(@PathVariable Long id) {
        wineService.deleteById(id);
    }
}
