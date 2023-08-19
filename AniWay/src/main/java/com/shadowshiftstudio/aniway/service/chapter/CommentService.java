package com.shadowshiftstudio.aniway.service.chapter;

import com.shadowshiftstudio.aniway.dto.comment.CommentDto;
import com.shadowshiftstudio.aniway.dto.comment.CreateCommentRequest;
import com.shadowshiftstudio.aniway.dto.auth.UpdateCommentRequest;
import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.AchievementType;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.chapter.ChapterNotFoundException;
import com.shadowshiftstudio.aniway.exception.comment.CommentNotFoundException;
import com.shadowshiftstudio.aniway.exception.comment.NoPermissionsToDeleteCommentException;
import com.shadowshiftstudio.aniway.exception.comment.UserCommentsAreNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.TitleNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.chapter.ChapterRepository;
import com.shadowshiftstudio.aniway.repository.chapter.CommentRepository;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.user.AchievementService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TitleRepository titleRepository;

    public CommentDto getComment(@NotNull Long id) throws CommentNotFoundException {
        try {
            CommentEntity comment = commentRepository.findById(id).get();
            return CommentDto.toDto(comment);
        } catch(NoSuchElementException e) {
            throw new CommentNotFoundException("Comment not found!");
        }
    }

    public String createComment(CreateCommentRequest request) throws UserNotFoundException, TitleNotFoundException, ChapterNotFoundException {
        UserEntity author = userRepository.findById(request.author).orElseThrow(() -> new UserNotFoundException("User not found"));
        CommentEntity comment = CommentEntity
                .builder()
                .text(request.text)
                .title(
                        request.getTitle_id() != 0
                                ? titleRepository.findById(request.getTitle_id()).orElseThrow(() -> new TitleNotFoundException("Title not found"))
                                : null
                )
                .chapter(
                        request.getChapter_id() != 0
                                ? chapterRepository.findById(request.getChapter_id()).orElseThrow(() -> new ChapterNotFoundException("Chapter not found"))
                                : null
                )
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .build();

        userRepository.save(author.addComment(comment));
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

    public List<CommentDto> getUserComments(String username, int page, int pageSize) throws UserNotFoundException, UserCommentsAreNotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("created_at"));

        List<CommentDto> comments = commentRepository
                .findByAuthor(userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User not found")),
                        pageable
                )
                .stream()
                .map(CommentDto::toDto)
                .toList();

        if (comments.isEmpty())
            throw new UserCommentsAreNotFoundException("User comments are not found");

        return comments;
    }

    public List<CommentDto> getTitleComments(Long titleId, int page, int pageSize) throws TitleNotFoundException, CommentNotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("created_at"));

        List<CommentDto> comments = commentRepository.findByTitle(
                titleRepository
                        .findById(titleId)
                        .orElseThrow(() -> new TitleNotFoundException("Title not found")),
                pageable
        ).stream().map(CommentDto::toDto).toList();

        if (comments.isEmpty())
            throw new CommentNotFoundException("Title comments not found");

        return comments;
    }
}
