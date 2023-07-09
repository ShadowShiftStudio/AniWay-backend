package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserEntity;
import com.shadowshiftstudio.aniway.enums.Sex;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
public class UserDto {
    private String username;
    private Sex sex;
    private int xp;
    private int pass_xp;
    private int balance;
    private boolean is_hentai_hidden;
    private boolean is_yuri_hidden;
    private boolean is_yaoi_hidden;
    private LocalDateTime created_at;

    public static UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.username = entity.getUsername();
        dto.sex = entity.getSex();
        dto.xp = entity.getXp();
        dto.pass_xp = entity.getPass_xp();
        dto.balance = entity.getBalance();
        dto.is_hentai_hidden = entity.is_hentai_hidden();
        dto.is_yuri_hidden = entity.is_yuri_hidden();
        dto.is_yaoi_hidden = entity.is_yaoi_hidden();
        dto.created_at = entity.getCreated_at();

        return dto;
    }
}
