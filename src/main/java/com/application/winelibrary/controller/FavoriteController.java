package com.application.winelibrary.controller;

import com.application.winelibrary.dto.favorite.FavoriteResponseDto;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.favorite.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Favorite management", description = "Endpoints for managing favorite")
@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{wineId}")
    @Operation(summary = "Add to favorite", description = "User access. Adds wine to favorite")
    public FavoriteResponseDto addToFavorite(Authentication authentication,
                                             @PathVariable Long wineId) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.addToFavorite(user.getId(), wineId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get favorite",
            description = "User access. Returns a list of user's favorite")
    public List<FavoriteResponseDto> getFavorite(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return favoriteService.getFavorite(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{wineId}")
    @Operation(summary = "Remove from favorite",
            description = "User access. Removes wine from user's favorite")
    public void removeFromFavorite(Authentication authentication,
                                   @PathVariable Long wineId) {
        User user = (User) authentication.getPrincipal();
        favoriteService.removeFromFavorites(user.getId(), wineId);
    }
}
