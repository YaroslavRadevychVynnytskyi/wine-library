package com.application.winelibrary.service.wine.impl;

import com.application.winelibrary.dto.food.FoodDto;
import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.dto.wine.WineSearchParameters;
import com.application.winelibrary.entity.Food;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.mapper.WineMapper;
import com.application.winelibrary.repository.food.FoodRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.repository.wine.WineSpecificationBuilder;
import com.application.winelibrary.service.wine.WineService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService {
    private final FoodRepository foodRepository;
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    private final WineSpecificationBuilder wineSpecificationBuilder;

    @Override
    public WineDetailedResponseDto save(CreateWineRequestDto requestDto) {
        Wine wine = wineMapper.toModel(requestDto);

        Set<Food.FoodName> foodNames = getFoodNames(requestDto.recommendedFood());
        wine.setRecommendedFood(foodRepository.findFoodsByNames(foodNames));

        Wine savedWine = wineRepository.save(wine);
        return wineMapper.toDto(savedWine);
    }

    @Override
    public List<WineDetailedResponseDto> findAll(Pageable pageable) {
        Page<Wine> wines = wineRepository.findAll(pageable);
        return wines.stream()
                .map(wineMapper::toDto)
                .toList();
    }

    @Override
    public WineDetailedResponseDto findById(Long id) {
        Wine wine = getWineById(id);
        return wineMapper.toDto(wine);
    }

    @Override
    public WineDetailedResponseDto updateById(Long id, CreateWineRequestDto requestDto) {
        Wine wine = getWineById(id);
        wineMapper.updateWineOutOfDto(requestDto, wine);

        Set<Food.FoodName> foodNames = getFoodNames(requestDto.recommendedFood());
        wine.setRecommendedFood(foodRepository.findFoodsByNames(foodNames));

        wineRepository.save(wine);
        return wineMapper.toDto(wine);
    }

    @Override
    public void deleteById(Long id) {
        wineRepository.deleteById(id);
    }

    @Override
    public List<WineDetailedResponseDto> search(WineSearchParameters searchParameters) {
        Specification<Wine> wineSpec = wineSpecificationBuilder.build(searchParameters);
        return wineRepository.findAll(wineSpec).stream()
                .map(wineMapper::toDto)
                .toList();
    }

    private Wine getWineById(Long id) {
        return wineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find wine with ID: " + id));
    }

    private Set<Food.FoodName> getFoodNames(Set<FoodDto> foodDtos) {
        return foodDtos.stream()
                .map(FoodDto::name)
                .map(Food.FoodName::valueOf)
                .collect(Collectors.toSet());
    }
}
