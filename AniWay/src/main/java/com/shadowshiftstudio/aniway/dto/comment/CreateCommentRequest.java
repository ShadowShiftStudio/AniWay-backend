package com.shadowshiftstudio.aniway.dto.comment;


import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
public class CreateCommentRequest {
    public Long author;
    public String text;
}
