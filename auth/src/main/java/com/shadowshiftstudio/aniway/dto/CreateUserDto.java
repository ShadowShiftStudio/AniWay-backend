package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entities.enums.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
  private String username;

  private String email;

  private String password;

  private Sex sex;
}
