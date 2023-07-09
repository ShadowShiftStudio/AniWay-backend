package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {
    private String username;
    private String badge;
    private Sex sex;
    private boolean is_hentai_hidden;
    private boolean is_yuri_hidden;
    private boolean is_yaoi_hidden;

}
