package com.application.winelibrary.dto.comment;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostCommentRequestDto(
        @NotBlank
        @Length(min = 4, max = 700)
        String text,

        @Length(max = 200)
        String advantages,

        @Length(max = 200)
        String disadvantages
) {
}
