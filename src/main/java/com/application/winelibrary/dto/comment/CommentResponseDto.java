package com.application.winelibrary.dto.comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long wineId,
        Long userId,
        String text,
        LocalDateTime createdAt
) {
}
