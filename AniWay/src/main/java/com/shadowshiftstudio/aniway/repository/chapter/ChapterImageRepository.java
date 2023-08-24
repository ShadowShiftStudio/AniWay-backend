package com.shadowshiftstudio.aniway.repository.chapter;

import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.chapter.ChapterImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterImageRepository extends JpaRepository<ChapterImageEntity, Long> {
    List<ChapterImageEntity> findByImageChapter(ChapterEntity entity);
    Optional<ChapterImageEntity> findFirstByOrderByImageIndexDesc();
}
