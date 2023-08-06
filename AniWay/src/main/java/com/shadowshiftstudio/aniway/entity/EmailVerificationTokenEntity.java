package com.shadowshiftstudio.aniway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="email_verif_tokens")

public class EmailVerificationTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(mappedBy = "email_verif_token_id")
    private UserEntity user;

    @Column(name="expiry_date")
    private Date expiryDate;

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiryDate.getTime();
    }
}
