package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_info_of_chapters")
public class UserInfoOfChaptersEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="chapter_id")
    private TitleEntity title;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="is_liked")
    private boolean isLiked;

    @Column(name="is_read")
    private boolean isRead;
}
