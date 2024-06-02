package com.application.winelibrary.dto.wine;

public record AddWineToCartRequestDto(
        Long wineId,
        Integer quantity
) {
}
