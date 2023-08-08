package com.shadowshiftstudio.aniway.repository;

import com.shadowshiftstudio.aniway.entity.TitleEntity;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {

//    @Query("FROM TitleEntity title " +
//            "JOIN title.genres genre " +
//            "JOIN title.categories category " +
//            "WHERE genre.name IN :genres and " +
//            "title.status IN :statuses and " +
//            "title.type IN :types and " +
//            "category.text IN :categories and " +
//            "title.ageRating IN :ageRatings")
//    List<Optional<TitleEntity>> findSpecificTitles(
//        @Param("genres") List<String> genres,
//        @Param("statuses") List<TitleStatus> statuses,
//        @Param("types") List<TitleType> types,
//        @Param("categories") List<String> categories,
//        @Param("ageRatings") List<AgeRating> ageRatings
////        SortBy sortBy
//    );

    Optional<TitleEntity> findById(Long id);
    Optional<TitleEntity> findByName(String name);
}
