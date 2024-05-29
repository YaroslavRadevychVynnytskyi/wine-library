package com.application.winelibrary.dto.wine;

import com.application.winelibrary.dto.food.FoodDto;
import com.application.winelibrary.entity.Wine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

public record CreateWineRequestDto(
        @NotBlank
        String name,

        @NotBlank
        String trademark,

        @NotBlank
        String country,

        @NotNull
        Year year,

        @NotNull
        Integer liquidVolume,

        @NotNull
        Integer alcoholContent,

        @NotNull
        Wine.WineType wineType,

        @NotNull
        Set<FoodDto> recommendedFood,

        Wine.Sweetness sweetness,

        Wine.Acidity acidity,

        String description,

        @NotNull
        Integer inventory,

        @NotNull
        BigDecimal price,

        String imageUrl
) {
}
