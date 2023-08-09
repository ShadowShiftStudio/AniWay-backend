package com.shadowshiftstudio.aniway.entity.auth;

import com.shadowshiftstudio.aniway.entity.user.UserEntity;
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
@Table(name="password_reset_tokens")

public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="expiry_date")
    private Date expiryDate;

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiryDate.getTime();
    }
}
