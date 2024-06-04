package com.application.winelibrary.repository.comment;

import com.application.winelibrary.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentsByWineId(Long wineId);
}
