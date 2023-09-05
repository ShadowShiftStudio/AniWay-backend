package com.shadowshiftstudio.aniway.repository.chapter;

import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.user.UserChapter;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.keys.UserChapterKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserChapterRepository extends JpaRepository<UserChapter, UserChapterKey> {
    Optional<UserChapter> findByUserAndChapter(UserEntity user, ChapterEntity chapter);
}
