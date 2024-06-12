package com.application.winelibrary.dto.selection;

import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import java.util.List;

public record SelectionResponseDto(
        String advice,
        List<WineDetailedResponseDto> wines
) {
}
