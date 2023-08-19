package com.shadowshiftstudio.aniway.dto.title;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateTitleRequest {
    private String username;
    private Long titleId;
    private int rating;
}
