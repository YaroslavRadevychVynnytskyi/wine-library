package com.application.winelibrary.service.favorite.impl;

import com.application.winelibrary.dto.favorite.FavoriteResponseDto;
import com.application.winelibrary.entity.Favorite;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.exception.ExistingFavoriteException;
import com.application.winelibrary.mapper.FavoriteMapper;
import com.application.winelibrary.repository.favorite.FavoriteRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.favorite.FavoriteService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final WineRepository wineRepository;

    private final FavoriteMapper favoriteMapper;

    @Override
    public FavoriteResponseDto addToFavorite(Long userId, Long wineId) {
        checkExistence(userId, wineId);

        Favorite favorite = new Favorite();
        favorite.setUser(getUserById(userId));
        favorite.setWine(getWineById(wineId));
        favorite.setAddedAt(LocalDateTime.now());

        Favorite savedFavorite = favoriteRepository.save(favorite);

        return favoriteMapper.toDto(savedFavorite);
    }

    @Override
    public List<FavoriteResponseDto> getFavorite(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(favoriteMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void removeFromFavorites(Long userId, Long wineId) {
        if (isFavoriteOwner(userId, wineId)) {
            favoriteRepository.deleteByWineId(wineId);
        }
    }

    private boolean isFavoriteOwner(Long userId, Long wineId) {
        return favoriteRepository.findByUserIdAndWineId(userId, wineId).isPresent();
    }

    private void checkExistence(Long userId, Long wineId) {
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndWineId(userId, wineId);
        if (favorite.isPresent()) {
            throw new ExistingFavoriteException("Wine with ID: " + wineId
                    + " is already in user's favorites");
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Can't find user with ID: " + userId));
    }

    private Wine getWineById(Long wineId) {
        return wineRepository.findById(wineId).orElseThrow(() ->
                new EntityNotFoundException("Can't find wine with ID: " + wineId));
    }
}
