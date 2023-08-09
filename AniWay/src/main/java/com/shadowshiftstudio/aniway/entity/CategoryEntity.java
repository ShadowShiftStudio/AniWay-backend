package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
public class CategoryEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToMany
    private Set<TitleEntity> titles;
}
