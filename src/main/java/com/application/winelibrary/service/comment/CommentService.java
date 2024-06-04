package com.application.winelibrary.service.comment;

import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.dto.comment.PostCommentRequestDto;
import java.util.List;

public interface CommentService {
    CommentResponseDto postComment(Long userId, Long wineId, PostCommentRequestDto requestDto);

    List<CommentResponseDto> getAllComments(Long wineId);

    void deleteComment(Long commentId);
}
