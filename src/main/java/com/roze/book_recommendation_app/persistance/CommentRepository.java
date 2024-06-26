package com.roze.book_recommendation_app.persistance;

import com.roze.book_recommendation_app.persistance.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);
    List<Comment> findByUserId(UUID userId);
}
