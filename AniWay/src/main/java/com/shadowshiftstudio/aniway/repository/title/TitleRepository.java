package com.shadowshiftstudio.aniway.repository.title;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> , JpaSpecificationExecutor<TitleEntity> {

//    @Query("FROM TitleEntity title " +
//            "JOIN title.genres genre " +
//            "JOIN title.categories category " +
//            "WHERE (:genres IS NULL OR genre.name IN :genres) and " +
//            "(:statuses IS NULL OR title.status IN :statuses) and " +
//            "(:types IS NULL OR title.type IN :types) and " +
//            "(:categories IS NULL OR category.text IN :categories) and " +
//            "(:ageRatings IS NULL OR title.ageRating IN :ageRatings)")
//    List<TitleEntity> findSpecificTitles(
//            @Param("genres") List<String> genres,
//            @Param("statuses") List<TitleStatus> statuses,
//            @Param("types") List<TitleType> types,
//            @Param("categories") List<String> categories,
//            @Param("ageRatings") List<AgeRating> ageRatings
//    );


    Optional<TitleEntity> findById(Long id);
    Optional<TitleEntity> findByName(String name);

    List<TitleEntity> findAll(Specification<TitleEntity> spec, Sort sort);
}