package com.application.winelibrary.service.comment.impl;

import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.dto.comment.PostCommentRequestDto;
import com.application.winelibrary.entity.Comment;
import com.application.winelibrary.entity.Role;
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
        Comment comment = commentMapper.toModel(requestDto);
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
    public void deleteComment(Long userId, Long commentId) {
        User user = getUserById(userId);
        if (isAdmin(user)) {
            commentRepository.deleteById(commentId);
            return;
        }

        Comment comment = getCommentById(commentId);
        if (isCommentOwner(user, comment)) {
            commentRepository.deleteById(commentId);
        }
    }

    @Override
    public void putLike(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
    }

    @Override
    public void putDislike(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setDislikes(comment.getDislikes() + 1);
        commentRepository.save(comment);
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(Role.RoleName.ADMIN));
    }

    private boolean isCommentOwner(User user, Comment comment) {
        return comment.getUser().getId().equals(user.getId());
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

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find comment with ID: " + commentId));
    }
}
