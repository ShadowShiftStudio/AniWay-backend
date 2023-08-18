package com.shadowshiftstudio.aniway.entity.title;

import com.shadowshiftstudio.aniway.entity.CategoryEntity;
import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.chapter.GenreEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
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

    @Column(name="background_url")
    private String backgroundUrl;

    @Column(name="age_rating")
    @Enumerated(STRING)
    private AgeRating ageRating;

    @OneToMany(targetEntity = ChapterEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Set<ChapterEntity> chapters;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "titles_genres",
            joinColumns = @JoinColumn(name="title_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    private Set<GenreEntity> genres;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name="title_categories",
            joinColumns = @JoinColumn(name="title_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<CategoryEntity> categories;

    @OneToMany(mappedBy="title")
    private Set<CommentEntity> comments;

    @OneToMany
    @JoinTable(
            name = "related_titles",
            joinColumns = @JoinColumn(name="base_title_id"),
            inverseJoinColumns = @JoinColumn(name = "related_title_id")
    )
    private Set<TitleEntity> relatedTitles;

    @OneToMany(mappedBy = "title")
    private Set<UserTitle> titlesInfo;

    public TitleEntity addGenre(GenreEntity genre) {
        genres.add(genre);
        genre.getTitles().add(this);
        return this;
    }

    public TitleEntity addCategory(CategoryEntity category) {
        categories.add(category);
        category.getTitles().add(this);
        return this;
    }
}
