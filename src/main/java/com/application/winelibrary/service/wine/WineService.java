package com.application.winelibrary.service.wine;

import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.UpdateCountryImageRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.dto.wine.WineSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface WineService {
    WineDetailedResponseDto save(CreateWineRequestDto requestDto);

    List<WineDetailedResponseDto> findAll(Pageable pageable);

    WineDetailedResponseDto findById(Long id);

    WineDetailedResponseDto updateById(Long id, CreateWineRequestDto requestDto);

    void deleteById(Long id);

    List<WineDetailedResponseDto> search(WineSearchParameters searchParameters);

    WineDetailedResponseDto updateCountryImage(Long id, UpdateCountryImageRequestDto requestDto);
}
