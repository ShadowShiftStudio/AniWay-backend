package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.dto.chapter.CategoryDto;
import com.shadowshiftstudio.aniway.dto.chapter.GenreDto;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private long id;
    private String name;
    private String originalName;
    private int year;
    private String description;
    private TitleStatus status;
    private TitleType type;
    private int views;
    private double rating;
    private int userRating;
    private String backgroundUrl;
    private List<CategoryDto> categories;
    private List<GenreDto> genres;
    private AgeRating ageRating;
    private ReadingStatus readingStatus;
    private Map<Integer, Integer> ratings;

    public static TitleDto toDto(TitleEntity entity) {
        Set<UserTitle> titlesInfo = entity.getTitlesInfo();

        Map<Integer, Integer> ratingsCount = new HashMap<>();

        titlesInfo.forEach(userTitle -> {
            int rating = userTitle.getRating();
            ratingsCount.put(rating, ratingsCount.getOrDefault(rating, 0) + 1);
        });

        return TitleDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalName(entity.getOriginalName())
                .year(entity.getYear())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .views(entity.getTitlesInfo().size())
                .ageRating(entity.getAgeRating())
                .backgroundUrl(entity.getBackgroundUrl())
                .rating((double) entity
                        .getTitlesInfo()
                        .stream()
                        .map(UserTitle::getRating)
                        .reduce(0, Integer::sum) / entity.getTitlesInfo().size()
                )
                .ratings(ratingsCount)
                .categories(entity.getCategories()
                        .stream()
                        .map(CategoryDto::toDto)
                        .toList()
                )
                .genres(entity.getGenres()
                        .stream()
                        .map(GenreDto::toDto)
                        .toList()
                )
                .build();
    }

    public static TitleDto toDto(UserTitle userTitle) {
        TitleEntity entity = userTitle.getTitle();
        Set<UserTitle> titlesInfo = entity.getTitlesInfo();

        Map<Integer, Integer> ratingsCount = new HashMap<>();

        titlesInfo.forEach(titleInfo -> {
            int rating = titleInfo.getRating();
            ratingsCount.put(rating, ratingsCount.getOrDefault(rating, 0) + 1);
        });

        return TitleDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalName(entity.getOriginalName())
                .year(entity.getYear())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .views(entity.getTitlesInfo().size())
                .ageRating(entity.getAgeRating())
                .backgroundUrl(entity.getBackgroundUrl())
                .rating((double) entity
                        .getTitlesInfo()
                        .stream()
                        .map(UserTitle::getRating)
                        .reduce(0, Integer::sum) / entity.getTitlesInfo().size()
                )
                .ratings(ratingsCount)
                .categories(entity.getCategories()
                        .stream()
                        .map(CategoryDto::toDto)
                        .toList()
                )
                .genres(entity.getGenres()
                        .stream()
                        .map(GenreDto::toDto)
                        .toList()
                )
                .userRating(userTitle.getRating())
                .readingStatus(userTitle.getReadingStatus())
                .build();
    }
}
