package com.application.winelibrary.service.selection;

import com.application.winelibrary.dto.selection.SelectionQueryRequestDto;
import com.application.winelibrary.dto.selection.SelectionResponseDto;

public interface SelectionService {
    SelectionResponseDto sendRequest(SelectionQueryRequestDto requestDto);
}
