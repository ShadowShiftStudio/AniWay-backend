package com.shadowshiftstudio.aniway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreateChapterRequest {
    private long title_id;
    private long user_id;
    private long team_id;

    private String name;
    private int number;
    private int volume;
}
