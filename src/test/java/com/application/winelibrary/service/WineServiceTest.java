package com.application.winelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.application.winelibrary.dto.food.FoodDto;
import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.entity.Food;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.mapper.WineMapper;
import com.application.winelibrary.repository.food.FoodRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.wine.impl.WineServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class WineServiceTest {
    @Mock
    private FoodRepository foodRepository;
    @Mock
    private WineRepository wineRepository;
    @Mock
    private WineMapper wineMapper;
    @Mock
    private Pageable pageable;
    @InjectMocks
    private WineServiceImpl wineService;

    @Test
    @DisplayName("Verify that correct WineDetailedResponseDto was returned by save() "
            + "when passed correct CreateWineRequestDto")
    void save_CorrectWineDetailedResponseDto_ShouldReturnCorrectWineDetailedResponseDto() {
        //Given
        CreateWineRequestDto requestDto = getCreateWineRequestDtoMock();
        Wine model = getWineMock();

        when(wineMapper.toModel(requestDto)).thenReturn(model);
        when(foodRepository.findFoodsByNames(Set.of(Food.FoodName.COLD_SNACKS)))
                .thenReturn(model.getRecommendedFood());
        model.setId(1L);
        when(wineRepository.save(model)).thenReturn(model);
        WineDetailedResponseDto expected = getWineDetailedResponseDtoMock();
        when(wineMapper.toDto(model)).thenReturn(expected);

        //When
        WineDetailedResponseDto actual = wineService.save(requestDto);

        //Then
        assertEquals(expected, actual);

        verify(wineMapper, times(1)).toModel(requestDto);
        verify(foodRepository, times(1)).findFoodsByNames(Set.of(Food.FoodName.COLD_SNACKS));
        verify(wineRepository, times(1)).save(model);
        verify(wineMapper, times(1)).toDto(model);
    }

    @Test
    @DisplayName("Verify the correct list of WineDetailedResponseDto was returned by findAll()")
    void findAll_CorrectListOfWineDetailedResponseDto_ShouldReturnListWithCorrectObjects() {
        //Given
        Page<Wine> wines = new PageImpl<>(List.of(getWineMock()));
        List<WineDetailedResponseDto> expected = List.of(getWineDetailedResponseDtoMock());

        when(wineRepository.findAll(pageable)).thenReturn(wines);
        when(wineMapper.toDto(wines.stream().findAny().get()))
                .thenReturn(getWineDetailedResponseDtoMock());

        //When
        List<WineDetailedResponseDto> actual = wineService.findAll(pageable);

        //Then
        assertEquals(expected, actual);

        verify(wineRepository, times(1)).findAll(pageable);
        verify(wineMapper, times(1)).toDto(wines.stream().findAny().get());
    }

    @Test
    @DisplayName("Verify the correct WineDetailedResponseDto was returned by findById()")
    void findById_CorrectWineDetailedResponseDto_ShouldReturnCorrectObject() {
        //Given
        Long id = 1L;
        Wine wine = getWineMock();
        WineDetailedResponseDto expected = getWineDetailedResponseDtoMock();

        when(wineRepository.findById(id)).thenReturn(Optional.of(wine));
        when(wineMapper.toDto(wine)).thenReturn(getWineDetailedResponseDtoMock());

        //When
        WineDetailedResponseDto actual = wineService.findById(id);

        assertEquals(expected, actual);

        verify(wineRepository, times(1)).findById(id);
        verify(wineMapper, times(1)).toDto(wine);
    }

    @Test
    @DisplayName("Verify findById() throws an exception when passing invalid ID")
    void findById_ThrowsEntityNotFoundException_ShouldThrowEntityNotFoundExceptionWhenInvalidId() {
        //Given
        Long invalidId = 999L;

        when(wineRepository.findById(invalidId)).thenReturn(Optional.empty());

        //When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> wineService.findById(invalidId));

        //Then
        String expected = "Can't find wine with ID: " + invalidId;
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        verify(wineRepository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(wineRepository);
    }

    private CreateWineRequestDto getCreateWineRequestDtoMock() {
        return new CreateWineRequestDto(
                "Bolgrad Costa Sur",
                "Bolgrad",
                "Ukraine",
                Year.of(2024),
                1000,
                14,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );
    }

    private Wine getWineMock() {
        Wine wine = new Wine();
        wine.setName("Bolgrad Costa Sur");
        wine.setTrademark("Bolgrad");
        wine.setCountry("Ukraine");
        wine.setYear(Year.of(2024));
        wine.setLiquidVolume(1000);
        wine.setWineType(Wine.WineType.RED);

        Food food = new Food();
        food.setId(1L);
        food.setName(Food.FoodName.COLD_SNACKS);
        wine.setRecommendedFood(Set.of(food));

        wine.setSweetness(Wine.Sweetness.DRY);
        wine.setAcidity(Wine.Acidity.MEDIUM);
        wine.setDescription("Good cheep wine");
        wine.setInventory(340);
        wine.setPrice(BigDecimal.valueOf(200));
        wine.setImageUrl("https://content1.rozetka.com.ua/goods/images/big/174123270.jpg");
        return wine;
    }

    private WineDetailedResponseDto getWineDetailedResponseDtoMock() {
        return new WineDetailedResponseDto(
                1L,
                "Bolgrad Costa Sur",
                "Bolgrad",
                "Ukraine",
                Year.of(2024),
                1000,
                14,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );
    }
}
