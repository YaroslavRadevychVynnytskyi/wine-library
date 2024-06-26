package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.dto.comment.PostCommentRequestDto;
import com.application.winelibrary.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    @Mapping(target = "wineId", source = "wine.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.firstName")
    CommentResponseDto toDto(Comment comment);

    Comment toModel(PostCommentRequestDto requestDto);
}
