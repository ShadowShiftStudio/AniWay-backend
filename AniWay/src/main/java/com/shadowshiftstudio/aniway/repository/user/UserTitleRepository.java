package com.shadowshiftstudio.aniway.repository.user;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.entity.user.keys.UserTitleKey;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTitleRepository extends JpaRepository<UserTitle, UserTitleKey> {
    List<UserTitle> findAllByUserAndReadingStatus(UserEntity user, ReadingStatus readingStatus);

    Optional<UserTitle> findByUserAndTitle(UserEntity user, TitleEntity title);
}
