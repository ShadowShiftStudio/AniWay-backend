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
@Table(name="teams")
public class TeamEntity {
    @Id
    @GeneratedValue
    private long id;

    // TODO LINK
    @Column(name="owner_id")
    private long ownerId;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    private String name;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 150, message = "{validation.name.size.too_long}")
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
