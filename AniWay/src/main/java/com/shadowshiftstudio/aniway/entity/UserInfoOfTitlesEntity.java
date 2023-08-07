package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import jakarta.persistence.*;
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
@Table(name="user_info_of_titles")
public class UserInfoOfTitlesEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="title_id")
    private TitleEntity title;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="reading_status")
    @Enumerated(STRING)
    private ReadingStatus readingStatus;

    private boolean bookmarked;

    private int rating;
}
