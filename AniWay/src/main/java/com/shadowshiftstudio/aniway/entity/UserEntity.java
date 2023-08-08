package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    private String username;

    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String email;

    @Column(name="email_verified")
    private boolean emailVerified;

    @Size(min = 10, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String password;

    @Enumerated(STRING)
    private Sex sex;
    private int xp;

    @Column(name="pass_xp")
    private int passXp;
    private int balance;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "users_badges",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="badge_id")
    )
    private Set<BadgeEntity> badges;

    @OneToMany(mappedBy = "user")
    private Set<UserInfoOfTitlesEntity> titlesInfo;

    @OneToMany(mappedBy = "user")
    private Set<UserInfoOfChaptersEntity> chaptersInfo;

    @Enumerated(STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="password_reset_token_id", referencedColumnName = "id")
    private PasswordResetTokenEntity passwordResetToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="email_verif_token_id", referencedColumnName = "id")
    private EmailVerificationTokenEntity emailVerificationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
