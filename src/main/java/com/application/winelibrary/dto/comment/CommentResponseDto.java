package com.application.winelibrary.dto.comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long wineId,
        Long userId,
        String userName,
        String text,
        String advantages,
        String disadvantages,
        Integer likes,
        Integer dislikes,
        LocalDateTime createdAt
) {
}
