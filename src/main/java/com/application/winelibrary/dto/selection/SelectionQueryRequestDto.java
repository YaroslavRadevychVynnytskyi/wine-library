package com.application.winelibrary.dto.selection;

import org.hibernate.validator.constraints.Length;

public record SelectionQueryRequestDto(
        @Length(min = 10, max = 1000)
        String userQuery
) {
}
