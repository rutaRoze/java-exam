package com.roze.book_recommendation_app.service;

import com.roze.book_recommendation_app.dto.request.CommentRequest;
import com.roze.book_recommendation_app.dto.response.CommentResponse;
import com.roze.book_recommendation_app.mapper.CommentMapper;
import com.roze.book_recommendation_app.persistance.BookRepository;
import com.roze.book_recommendation_app.persistance.CommentRepository;
import com.roze.book_recommendation_app.persistance.UserRepository;
import com.roze.book_recommendation_app.persistance.entity.Book;
import com.roze.book_recommendation_app.persistance.entity.Comment;
import com.roze.book_recommendation_app.persistance.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
                          BookRepository bookRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponse saveComment(CommentRequest commentRequest) {
        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID: " + commentRequest.getUserId()));

        Book book = bookRepository.findById(commentRequest.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found by ID: " + commentRequest.getBookId()));

        Comment commentToSave = commentMapper.requestToComment(commentRequest, user, book);
        Comment savedComment = commentRepository.save(commentToSave);

        return commentMapper.commentToResponse(savedComment);
    }
}
