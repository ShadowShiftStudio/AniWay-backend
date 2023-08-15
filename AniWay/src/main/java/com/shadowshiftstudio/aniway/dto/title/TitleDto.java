package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.dto.chapter.CategoryDto;
import com.shadowshiftstudio.aniway.dto.chapter.GenreDto;
import com.shadowshiftstudio.aniway.dto.comment.CommentDto;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String backgroundUrl;
    private List<CommentDto> comments;
    private List<CategoryDto> categories;
    private List<GenreDto> genres;
    private AgeRating ageRating;
    public static TitleDto toDto(TitleEntity entity) {
        return TitleDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalName(entity.getOriginalName())
                .year(entity.getYear())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .views(entity.getViews())
                .ageRating(entity.getAgeRating())
                .backgroundUrl(entity.getBackgroundUrl())
                .comments(entity.getComments()
                        .stream()
                        .map(CommentDto::toDto)
                        .toList()
                )
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
}
