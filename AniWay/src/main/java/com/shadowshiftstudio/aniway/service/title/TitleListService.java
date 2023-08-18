package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.title.TitleDto;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import com.shadowshiftstudio.aniway.exception.title.TitlesNotFoundException;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
public class TitleListService {
    @Autowired
    private TitleRepository titleRepository;

    public List<TitleDto> getCatalogTitles(
            List<String> genres,
            List<TitleStatus> statuses,
            List<TitleType> types,
            List<String> categories,
            List<AgeRating> ratings
    ) throws TitlesNotFoundException {
        Specification<TitleEntity> specification = buildSpecification(genres, statuses, types, categories, ratings);

        Sort sort = Sort.by("ageRating");
        List<TitleDto> titles = titleRepository.findAll(specification, sort)
                .stream()
                .map(TitleDto::toDto)
                .collect(Collectors.toList());

        if (titles.isEmpty()) {
            throw new TitlesNotFoundException("Titles not found");
        }

        return titles;
    }

    private Specification<TitleEntity> buildSpecification(
            List<String> genres,
            List<TitleStatus> statuses,
            List<TitleType> types,
            List<String> categories,
            List<AgeRating> ratings
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (genres != null && !genres.isEmpty()) {
                predicates.add(root.join("genres").get("name").in(genres));
            }

            if (statuses != null && !statuses.isEmpty()) {
                predicates.add(root.get("status").in(statuses));
            }

            if (types != null && !types.isEmpty()) {
                predicates.add(root.get("type").in(types));
            }

            if (categories != null && !categories.isEmpty()) {
                predicates.add(root.join("categories").get("text").in(categories));
            }

            if (ratings != null && !ratings.isEmpty()) {
                predicates.add(root.get("ageRating").in(ratings));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

