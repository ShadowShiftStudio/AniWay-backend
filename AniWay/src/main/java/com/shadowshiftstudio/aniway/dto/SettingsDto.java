package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserEntity;
import com.shadowshiftstudio.aniway.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsDto {
    private String username;
    private String badge;
    private Sex sex;
    private boolean isHentaiHidden;
    private boolean isYuriHidden;
    private boolean isYaoiHidden;

    public static SettingsDto getSettingsDto(UserEntity entity) {
        SettingsDto dto = new SettingsDto();

        dto.username = entity.getUsername();
        //TODO

        return dto;
    }
}
