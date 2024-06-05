package com.application.winelibrary.dto.wine;

import com.application.winelibrary.entity.Wine;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Year;

public record WineSearchParameters(
        @Min(0) @Max(100_000)
        Double minPrice,
        @Min(0) @Max(100_000)
        Double maxPrice,
        Wine.WineType[] colors,
        Wine.Sweetness[] sweetness,
        String[] countries,
        Year[] vintages
) {
}
