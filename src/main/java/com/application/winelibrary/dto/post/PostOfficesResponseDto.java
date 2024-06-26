package com.application.winelibrary.dto.post;

import java.util.Set;

public record PostOfficesResponseDto(
        Set<PostOfficeDto> ukrPostOffices,
        Set<PostOfficeDto> novaPostOffices
) {
}
