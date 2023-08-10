package com.shadowshiftstudio.aniway.entity.user;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.keys.UserTitleKey;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_info_of_titles")
public class UserTitle {
    @EmbeddedId
    private UserTitleKey id;

    @ManyToOne
    @MapsId("titleId")
    @JoinColumn(name = "title_id")
    private TitleEntity title;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reading_status")
    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;

    private int rating;
}
