package com.shadowshiftstudio.aniway.repository.title;

import com.shadowshiftstudio.aniway.entity.chapter.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRespository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findById(Long id);

    Optional<GenreEntity> findByName(String name);
}
