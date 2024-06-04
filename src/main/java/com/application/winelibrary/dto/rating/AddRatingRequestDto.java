package com.application.winelibrary.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AddRatingRequestDto(
        @Min(1) @Max(5)
        Integer rating
) {
}
