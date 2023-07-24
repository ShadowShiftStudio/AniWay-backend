package com.shadowshiftstudio.aniway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokensPairDto {
  private String AccessToken;

  private String RefreshToken;
}
