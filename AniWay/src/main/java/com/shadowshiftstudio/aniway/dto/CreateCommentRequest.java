package com.shadowshiftstudio.aniway.dto;


import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
public class CreateCommentRequest {
    public Long author;
    public String text;
}
