package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.entity.Wine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface WineMapper {
    @Mapping(target = "recommendedFood", ignore = true)
    Wine toModel(CreateWineRequestDto requestDto);

    WineDetailedResponseDto toDto(Wine wine);

    @Mapping(target = "id", ignore = true)
    void updateWineOutOfDto(CreateWineRequestDto wineDto, @MappingTarget Wine wine);
}
