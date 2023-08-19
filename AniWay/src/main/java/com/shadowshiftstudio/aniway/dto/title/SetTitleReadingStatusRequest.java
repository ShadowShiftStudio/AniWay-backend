package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetTitleReadingStatusRequest {
    private String username;
    private Long titleId;
    private ReadingStatus status;
}
