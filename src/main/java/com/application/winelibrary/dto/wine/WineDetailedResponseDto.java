package com.application.winelibrary.dto.wine;

import com.application.winelibrary.dto.food.FoodDto;
import com.application.winelibrary.entity.Wine;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

public record WineDetailedResponseDto(
        Long id,
        String name,
        String trademark,
        String country,
        Year year,
        Integer liquidVolume,
        Integer alcoholContent,
        Wine.WineType wineType,
        Set<FoodDto> recommendedFood,
        Wine.Sweetness sweetness,
        Wine.Acidity acidity,
        String description,
        Integer inventory,
        BigDecimal price,
        String imageUrl
) {
}
