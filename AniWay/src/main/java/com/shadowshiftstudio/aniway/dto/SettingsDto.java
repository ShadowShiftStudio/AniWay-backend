package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {
    private String username;
    private String badge;
    private Sex sex;
    private boolean isHentaiHidden;
    private boolean isYuriHidden;
    private boolean isYaoiHidden;

    public static SettingsDto getSettingsDto(Long id) {

    }
}
