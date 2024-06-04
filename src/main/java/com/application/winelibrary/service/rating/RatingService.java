package com.application.winelibrary.service.rating;

import com.application.winelibrary.dto.rating.AddRatingRequestDto;
import com.application.winelibrary.dto.rating.AverageRatingResponseDto;
import com.application.winelibrary.dto.rating.RatingResponseDto;

public interface RatingService {
    RatingResponseDto addRating(Long userId, Long wineId, AddRatingRequestDto requestDto);

    AverageRatingResponseDto getAverageRating(Long wineId);
}
