package com.application.winelibrary.service.comment.impl;

import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.dto.comment.PostCommentRequestDto;
import com.application.winelibrary.entity.Comment;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.mapper.CommentMapper;
import com.application.winelibrary.repository.comment.CommentRepository;
import com.application.winelibrary.repository.user.UserRepository;
import com.application.winelibrary.repository.wine.WineRepository;
import com.application.winelibrary.service.comment.CommentService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WineRepository wineRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto postComment(Long userId,
                                          Long wineId,
                                          PostCommentRequestDto requestDto) {
        Comment comment = new Comment();
        comment.setText(requestDto.text());
        comment.setUser(getUserById(userId));
        comment.setWine(getWineById(wineId));
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public List<CommentResponseDto> getAllComments(Long wineId) {
        List<Comment> comments = commentRepository.findAllCommentsByWineId(wineId);
        return comments.stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user with ID: " + userId));
    }

    private Wine getWineById(Long wineId) {
        return wineRepository.findById(wineId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find wine with ID: " + wineId));
    }
}
