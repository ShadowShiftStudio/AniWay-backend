package com.shadowshiftstudio.aniway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comment")
public class CommentEntity {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private UserEntity author;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 300, message = "{validation.name.size.too_long}")
    private String text;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
