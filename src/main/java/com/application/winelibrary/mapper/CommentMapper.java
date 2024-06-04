package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    @Mapping(target = "wineId", source = "wine.id")
    @Mapping(target = "userId", source = "user.id")
    CommentResponseDto toDto(Comment comment);
}
