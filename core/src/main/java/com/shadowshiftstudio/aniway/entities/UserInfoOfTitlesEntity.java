package com.shadowshiftstudio.aniway.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.shadowshiftstudio.aniway.entities.enums.*;

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
    private ReadingStatus readingStatus;

    private boolean bookmarked;

    private int rating;
}
