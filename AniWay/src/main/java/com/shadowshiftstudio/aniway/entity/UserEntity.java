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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    private String username;

    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String email;

    @Size(min = 10, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String password;

    @Enumerated(STRING)
    private Sex sex;
    private int xp;

    @Column(name="pass_xp")
    private int passXp;
    private int balance;

    @Column(name="is_hentai_hidden")
    private boolean isHentaiHidden;

    @Column(name="is_yuri_hidden")
    private boolean isYuriHidden;

    @Column(name="is_yaoi_hidden")
    private boolean isYaoiHidden;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

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
