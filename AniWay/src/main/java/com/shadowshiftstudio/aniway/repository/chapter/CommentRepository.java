package com.shadowshiftstudio.aniway.repository.chapter;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
     List<CommentEntity> findByAuthor(UserEntity author);
}
