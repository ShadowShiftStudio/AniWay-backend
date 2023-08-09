package com.shadowshiftstudio.aniway.service.chapter;

import com.shadowshiftstudio.aniway.dto.comment.CommentDto;
import com.shadowshiftstudio.aniway.dto.comment.CreateCommentRequest;
import com.shadowshiftstudio.aniway.dto.auth.UpdateCommentRequest;
import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.comment.CommentNotFoundException;
import com.shadowshiftstudio.aniway.exception.comment.NoPermissionsToDeleteCommentException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.chapter.CommentRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public String deleteComment(@NotNull Long user_id, @NotNull Long id) throws CommentNotFoundException, NoPermissionsToDeleteCommentException {
        CommentEntity commentEntity = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        UserEntity userEntity = commentEntity.getAuthor();

        if (userEntity.getRole() != Role.MODERATOR
                && !user_id.equals(userEntity.getId())) {
            throw new NoPermissionsToDeleteCommentException("User doesn't have permissions to delete comment");
        }

        commentRepository.deleteById(id);
        return "Comment deleted successfully";

    }

    public String updateComment(UpdateCommentRequest request) throws CommentNotFoundException, NoPermissionsToDeleteCommentException {
        CommentEntity comment = commentRepository.findById(request.getComment_id()).orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        if (!Objects.equals(comment.getAuthor().getId(), request.getUser_id())) {
            throw new NoPermissionsToDeleteCommentException("User doesn't have permissions to delete comment");
        }

        comment.setText(request.getText());
        comment.setUpdatedAt(new Date(System.currentTimeMillis()));
        commentRepository.save(comment);

        return "Comment was updated successfully";
    }

    public List<CommentDto> getUserComments(Long userId) throws UserNotFoundException {
        return commentRepository
                .findByAuthor(userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
                )
                .stream()
                .map(CommentDto::toDto)
                .toList();
    }
}
