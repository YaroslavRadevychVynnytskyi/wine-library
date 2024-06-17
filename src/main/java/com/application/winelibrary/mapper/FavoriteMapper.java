package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.favorite.FavoriteResponseDto;
import com.application.winelibrary.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface FavoriteMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "wineId", source = "wine.id")
    FavoriteResponseDto toDto(Favorite favorite);
}
