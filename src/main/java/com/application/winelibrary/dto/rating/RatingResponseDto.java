package com.application.winelibrary.dto.rating;

import java.time.LocalDateTime;

public record RatingResponseDto(
        Long id,
        Long wineId,
        Long userId,
        Integer rating,
        LocalDateTime createdAt
) {
}
