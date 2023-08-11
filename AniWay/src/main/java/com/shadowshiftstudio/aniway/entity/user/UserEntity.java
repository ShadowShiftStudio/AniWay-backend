package com.shadowshiftstudio.aniway.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shadowshiftstudio.aniway.entity.*;
import com.shadowshiftstudio.aniway.entity.auth.EmailVerificationTokenEntity;
import com.shadowshiftstudio.aniway.entity.auth.PasswordResetTokenEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude = {"comments"})
@ToString(exclude = {"comments"})
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

    private int lvl = xp / 500;

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
    private Set<UserTitle> titlesInfo;

    @OneToMany(mappedBy = "user")
    private Set<UserChapter> chaptersInfo;

    @Enumerated(STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"author"})
    private Set<CommentEntity> comments;

    public UserEntity addComment(CommentEntity comment) {
        comments.add(comment);
        comment.setAuthor(this);
        return this;
    }
    private String avatarUrl;

    private String backgroundUrl;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PasswordResetTokenEntity passwordResetToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EmailVerificationTokenEntity emailVerificationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @ManyToMany(mappedBy = "users")
    private Set<TeamEntity> teams;

    @OneToMany(mappedBy = "user")
    private Set<UserAchievement> achievementsInfo;

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
