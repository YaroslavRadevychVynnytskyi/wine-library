package com.application.winelibrary.dto.favorite;

import java.time.LocalDateTime;

public record FavoriteResponseDto(
        Long id,
        Long userId,
        Long wineId,
        LocalDateTime addedAt
) {
}
