package com.shadowshiftstudio.aniway.service.auth;

import com.shadowshiftstudio.aniway.dto.auth.AuthenticationRequest;
import com.shadowshiftstudio.aniway.dto.auth.AuthenticationResponse;
import com.shadowshiftstudio.aniway.dto.auth.EmailDetails;
import com.shadowshiftstudio.aniway.dto.auth.RegisterRequest;
import com.shadowshiftstudio.aniway.entity.auth.EmailVerificationTokenEntity;
import com.shadowshiftstudio.aniway.entity.auth.PasswordResetTokenEntity;
import com.shadowshiftstudio.aniway.entity.auth.RefreshToken;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.auth.EmailIsOccupiedException;
import com.shadowshiftstudio.aniway.exception.auth.UsernameIsOccupiedException;
import com.shadowshiftstudio.aniway.exception.tokens.EmailVerificationTokenIsExpiredException;
import com.shadowshiftstudio.aniway.exception.tokens.EmailVerificationTokenNotFoundException;
import com.shadowshiftstudio.aniway.exception.tokens.PasswordResetTokenIsExpiredException;
import com.shadowshiftstudio.aniway.exception.tokens.PasswordResetTokenNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.auth.EmailVerificationTokenRepository;
import com.shadowshiftstudio.aniway.repository.auth.PasswordResetTokenRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.user.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private String apiUrl = "https://localhost:8080";

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthenticationResponse register(RegisterRequest request) throws UsernameIsOccupiedException, EmailIsOccupiedException, UserNotFoundException {
        validateRegisterRequest(request);

        var user = UserEntity.builder()
                .username(request.getUsername())
                .emailVerified(false)
                .role(request.getRole())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .sex(request.getSex())
                .createdAt(new Date(System.currentTimeMillis()))
                .build();

        repository.save(user);
        achievementService.initUserAchievements(request.getUsername());

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .token(refreshToken.getToken())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .token(refreshToken.getToken())
                .build();
    }

    public String forgotPassword(String email) throws UserNotFoundException {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        PasswordResetTokenEntity token = generatePasswordResetToken(userEntity);
        passwordResetTokenRepository.save(token);
        String url = apiUrl + "/auth/reset_password?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(url, email, "Click the link to reset password: ", "Password reset");

        return emailService.sendMail(emailDetails);
    }

    public String validatePasswordResetToken(String token) throws PasswordResetTokenNotFoundException, PasswordResetTokenIsExpiredException {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new PasswordResetTokenNotFoundException("Password reset token not found"));;

        ValidatePasswordResetToken(tokenEntity);

        return "Token validated successfully";
    }

    public String resetPassword(String password, String token) throws PasswordResetTokenNotFoundException {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new PasswordResetTokenNotFoundException("Password reset token not found"));
        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setPasswordResetToken(null);
        repository.save(userEntity);

        passwordResetTokenRepository.delete(tokenEntity);

        return "Password reset succeeded";
    }

    public String sendVerificationEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        EmailVerificationTokenEntity token = generateEmailVerificationToken(userEntity);
        emailVerificationTokenRepository.save(token);
        String url = apiUrl + "/auth/verify_email?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(url, email, "Click the link to verify email: ", "Email verification");

        return emailService.sendMail(emailDetails);
    }
    public String validateEmailVerificationToken(String token) throws EmailVerificationTokenNotFoundException, EmailVerificationTokenIsExpiredException {
        EmailVerificationTokenEntity tokenEntity = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new EmailVerificationTokenNotFoundException("Email verification token not found"));

        ValidateEmailVerificationToken(tokenEntity);
        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setEmailVerified(true);
        userEntity.setEmailVerificationToken(null);
        repository.save(userEntity);
        emailVerificationTokenRepository.delete(tokenEntity);

        return "Token validated successfully";
    }
    private EmailVerificationTokenEntity generateEmailVerificationToken(UserEntity user) {
        return EmailVerificationTokenEntity
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .build();
    }

    private void ValidateEmailVerificationToken(EmailVerificationTokenEntity token) throws EmailVerificationTokenIsExpiredException {
        if (token.isExpired()) {
            emailVerificationTokenRepository.delete(token);
            throw new EmailVerificationTokenIsExpiredException("Password reset token is expired!");
        }
    }

    private void ValidatePasswordResetToken(PasswordResetTokenEntity token) throws PasswordResetTokenIsExpiredException {
        if (token.isExpired()) {
            passwordResetTokenRepository.delete(token);
            throw new PasswordResetTokenIsExpiredException("Password reset token is expired!");
        }
    }
    private EmailDetails getEmailDetails(String url, String email, String msgBody, String subject) {
        return EmailDetails
                .builder()
                .recipient(email)
                .msgBody(msgBody + url)
                .subject(subject)
                .build();
    }
    private PasswordResetTokenEntity generatePasswordResetToken(UserEntity user) {
        return PasswordResetTokenEntity
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .build();
    }
    private void validateRegisterRequest(RegisterRequest request) throws EmailIsOccupiedException, UsernameIsOccupiedException {
        if (repository.findByEmail(request.getEmail()).isPresent())
            throw new EmailIsOccupiedException("Account with this email is already registered");
        if (repository.findByUsername(request.getUsername()).isPresent())
            throw new UsernameIsOccupiedException("Account with this username is already registered");
    }
}
