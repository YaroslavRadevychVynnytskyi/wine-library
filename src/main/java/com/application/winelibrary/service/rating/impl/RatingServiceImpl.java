package com.application.winelibrary.service.rating.impl;

import com.application.winelibrary.dto.rating.AddRatingRequestDto;
import com.application.winelibrary.dto.rating.AverageRatingResponseDto;
import com.application.winelibrary.dto.rating.RatingResponseDto;
import com.application.winelibrary.entity.Rating;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.exception.ExistingRatingException;
import com.application.winelibrary.mapper.RatingMapper;
import com.application.winelibrary.repository.rating.RatingRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.rating.RatingService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final WineRepository wineRepository;

    private final RatingMapper ratingMapper;

    @Override
    public RatingResponseDto addRating(Long userId, Long wineId, AddRatingRequestDto requestDto) {
        checkIfAlreadyRatedByUserWithId(userId, wineId);

        Rating rating = new Rating();
        rating.setUser(getUserById(userId));
        rating.setWine(getWineById(wineId));
        rating.setRating(requestDto.rating());
        Rating savedRating = ratingRepository.save(rating);

        Wine wine = getWineById(wineId);
        wine.setAverageRating(calculateAverageRating(wine.getRatings()));
        wineRepository.save(wine);

        return ratingMapper.toDto(savedRating);
    }

    @Override
    public AverageRatingResponseDto getAverageRating(Long wineId) {
        Wine wine = getWineById(wineId);
        return new AverageRatingResponseDto(wine.getAverageRating());
    }

    private void checkIfAlreadyRatedByUserWithId(Long userId, Long wineId) {
        Optional<Rating> rating = ratingRepository.findByWineIdAndUserId(wineId, userId);
        if (rating.isPresent()) {
            throw new ExistingRatingException("User with ID: " + userId
                    + " already rated wine with ID: " + wineId);
        }
    }

    private Double calculateAverageRating(Set<Rating> ratings) {
        Integer sum = ratings.stream()
                .map(Rating::getRating)
                .reduce(Integer::sum)
                .orElseThrow(() -> new IllegalArgumentException("No ratings provided"));
        return (double) sum / ratings.size();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user with ID: " + userId));
    }

    private Wine getWineById(Long wineId) {
        return wineRepository.findById(wineId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find wine with ID: " + wineId));
    }
}
