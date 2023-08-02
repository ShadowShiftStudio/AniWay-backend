package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.CommentDto;
import com.shadowshiftstudio.aniway.dto.CreateCommentRequest;
import com.shadowshiftstudio.aniway.dto.UpdateCommentRequest;
import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.exception.CommentNotFoundException;
import com.shadowshiftstudio.aniway.repository.CommentRepository;
import com.shadowshiftstudio.aniway.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    public CommentDto getComment(@NotNull Long id) throws CommentNotFoundException {
        try {
            CommentEntity comment = commentRepository.findById(id).get();
            return CommentDto.toDto(comment);
        } catch(NoSuchElementException e) {
            throw new CommentNotFoundException("Comment not found!");
        }
    }

    public String createComment(CreateCommentRequest request) {
        commentRepository.save(CommentEntity
                .builder()
                .author(userRepository.findById(request.author).get())
                .text(request.text)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .build()
        );
        return "Comment was created";
    }

    public String deleteComment(@NotNull Long id) throws CommentNotFoundException {
        try {
            commentRepository.deleteById(id);
            return "Comment was deleted";
        } catch(NoSuchElementException e) {
            throw new CommentNotFoundException("Comment not found!");
        }
    }

    public String updateComment(UpdateCommentRequest request) throws CommentNotFoundException {
        Optional<CommentEntity> commentOptional = commentRepository.findById(request.getId());
        CommentEntity comment;

        if (commentOptional.isPresent())
            comment = commentOptional.get();
        else
            throw new CommentNotFoundException("Comment not found");

        comment.setText(request.getText());
        comment.setUpdatedAt(new Date(System.currentTimeMillis()));

        return "Comment was updated successfully";
    }
}
