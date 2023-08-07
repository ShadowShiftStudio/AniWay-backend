package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="titles")
public class TitleEntity {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String name;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    @Column(name="original_name")
    private String originalName;

    private int year;
    private String description;

    @Enumerated(STRING)
    private TitleStatus status;

    @Enumerated(STRING)
    private TitleType type;

    private int views;

    @Column(name="age_rating")
    @Enumerated(STRING)
    private AgeRating ageRating;

    @OneToMany(targetEntity = ChapterEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Set<ChapterEntity> chapters;

    @ManyToMany
    @JoinTable(
            name = "titles_genres",
            joinColumns = @JoinColumn(name="title_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    private Set<GenreEntity> genres;

    @ManyToMany
    @JoinTable(
            name="title_categories",
            joinColumns = @JoinColumn(name="title_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<CategoryEntity> categories;

    @OneToMany(targetEntity = TitleCommentsEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Set<TitleCommentsEntity> comments;

    @OneToMany
    @JoinTable(
            name = "related_titles",
            joinColumns = @JoinColumn(name="base_title_id"),
            inverseJoinColumns = @JoinColumn(name = "related_title_id")
    )
    private Set<TitleEntity> relatedTitles;

    @OneToMany(mappedBy = "title")
    private Set<UserInfoOfTitlesEntity> titlesInfo;

    @OneToMany(mappedBy = "title")
    private Set<UserInfoOfChaptersEntity> chaptersInfo;
}
