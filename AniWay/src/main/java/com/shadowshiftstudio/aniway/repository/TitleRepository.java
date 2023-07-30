package com.shadowshiftstudio.aniway.repository;

import com.shadowshiftstudio.aniway.entity.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {
    Optional<TitleEntity> findById(Long id);
    Optional<TitleEntity> findByName(String name);
}
