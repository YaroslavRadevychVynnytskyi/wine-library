package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.rating.RatingResponseDto;
import com.application.winelibrary.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RatingMapper {
    @Mapping(target = "wineId", source = "wine.id")
    @Mapping(target = "userId", source = "user.id")
    RatingResponseDto toDto(Rating rating);
}
