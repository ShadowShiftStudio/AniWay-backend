package com.shadowshiftstudio.aniway.repository;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
