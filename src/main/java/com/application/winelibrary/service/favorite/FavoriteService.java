package com.application.winelibrary.service.favorite;

import com.application.winelibrary.dto.favorite.FavoriteResponseDto;
import java.util.List;

public interface FavoriteService {
    FavoriteResponseDto addToFavorite(Long userId, Long wineId);

    List<FavoriteResponseDto> getFavorite(Long userId);

    void removeFromFavorites(Long userId, Long wineId);
}
