package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.*;
import com.shadowshiftstudio.aniway.entity.EmailVerificationTokenEntity;
import com.shadowshiftstudio.aniway.entity.PasswordResetTokenEntity;
import com.shadowshiftstudio.aniway.entity.RefreshToken;
import com.shadowshiftstudio.aniway.entity.UserEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.*;
import com.shadowshiftstudio.aniway.repository.EmailVerificationTokenRepository;
import com.shadowshiftstudio.aniway.repository.PasswordResetTokenRepository;
import com.shadowshiftstudio.aniway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;
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
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthenticationResponse register(RegisterRequest request) throws UsernameIsOccupiedException, EmailIsOccupiedException {
        validateRegisterRequest(request);
        // TODO ROLES
        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .sex(request.getSex())
                .createdAt(new Date(System.currentTimeMillis()))
                .build();

        repository.save(user);

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

    public String forgotPassword(ForgotPasswordRequest request) throws UserNotFoundException {
        Optional<UserEntity> userOptional = repository.findByEmail(request.getEmail());
        UserEntity userEntity;

        if (userOptional.isPresent())
            userEntity = userOptional.get();
        else
            throw new UserNotFoundException("User not found");

        PasswordResetTokenEntity token = generatePasswordResetToken(userEntity);
        String url = apiUrl + "/auth/resetpassword?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(request.getEmail(), url);

        return emailService.sendMail(emailDetails);
    }

    public String validatePasswordResetToken(String token) throws PasswordResetTokenNotFoundException, PasswordResetTokenIsExpiredException {
        Optional<PasswordResetTokenEntity> tokenOptional = passwordResetTokenRepository.findByToken(token);
        PasswordResetTokenEntity tokenEntity;

        if (tokenOptional.isPresent())
            tokenEntity = tokenOptional.get();
        else
            throw new PasswordResetTokenNotFoundException("Password reset token not found!");

        ValidatePasswordResetToken(tokenEntity);

        return "Token validated successfully";
    }

    public String resetPassword(String password, String token) {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token).get();
        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setPassword(passwordEncoder.encode(password));
        repository.save(userEntity);
        passwordResetTokenRepository.delete(tokenEntity);

        return "Password reset succeeded";
    }

    public String sendVerificationEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> userOptional = repository.findByEmail(email);
        UserEntity userEntity;

        if (userOptional.isPresent())
            userEntity = userOptional.get();
        else
            throw new UserNotFoundException("User not found");

        EmailVerificationTokenEntity token = generateEmailVerificationToken(userEntity);
        String url = apiUrl + "/auth/verify_email?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(email, url);

        return emailService.sendMail(emailDetails);
    }
    public String validateEmailVerificationToken(String token) throws EmailVerificationTokenNotFoundException, EmailVerificationTokenIsExpiredException {
        Optional<EmailVerificationTokenEntity> tokenOptional = emailVerificationTokenRepository.findByToken(token);
        EmailVerificationTokenEntity tokenEntity;

        if (tokenOptional.isPresent())
            tokenEntity = tokenOptional.get();
        else
            throw new EmailVerificationTokenNotFoundException("Password reset token not found!");

        ValidateEmailVerificationToken(tokenEntity);
        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setEmailVerified(true);
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
    private EmailDetails getEmailDetails(String email, String url) {
        return EmailDetails
                .builder()
                .recipient(email)
                .msgBody(MessageFormat.format("Click on the link to reset password: {0}", url))
                .subject("Aniway password reset")
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
