package com.shadowshiftstudio.aniway.entity.user.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserChapterKey implements Serializable {
    @Column(name="chapter_id")
    private Long chapterId;

    @Column(name="user_id")
    private Long userId;
}
