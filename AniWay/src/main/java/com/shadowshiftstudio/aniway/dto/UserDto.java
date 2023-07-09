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
    private boolean isHentaiHidden;
    private boolean isYuriHidden;
    private boolean isYaoiHidden;
    private LocalDateTime createdAt;

    public static UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.username = entity.getUsername();
        dto.sex = entity.getSex();
        dto.xp = entity.getXp();
        dto.pass_xp = entity.getPass_xp();
        dto.balance = entity.getBalance();
        dto.isHentaiHidden = entity.isHentaiHidden();
        dto.isYuriHidden = entity.isYuriHidden();
        dto.isYaoiHidden = entity.isYaoiHidden();
        dto.createdAt = entity.getCreatedAt();

        return dto;
    }
}
