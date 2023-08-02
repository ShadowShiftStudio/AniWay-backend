package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.AuthenticationRequest;
import com.shadowshiftstudio.aniway.dto.AuthenticationResponse;
import com.shadowshiftstudio.aniway.dto.RegisterRequest;
import com.shadowshiftstudio.aniway.entity.RefreshToken;
import com.shadowshiftstudio.aniway.entity.UserEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.AccountEmailExistsException;
import com.shadowshiftstudio.aniway.exception.UserNotFoundException;
import com.shadowshiftstudio.aniway.exception.UsernameIsOccupiedException;
import com.shadowshiftstudio.aniway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

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

    public AuthenticationResponse register(RegisterRequest request) throws UsernameIsOccupiedException, AccountEmailExistsException {
        validateRegisterRequest(request);
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
    private void validateRegisterRequest(RegisterRequest request) throws AccountEmailExistsException, UsernameIsOccupiedException {
        if (repository.findByEmail(request.getEmail()).isPresent())
            throw new AccountEmailExistsException("Account with this email is already registered");
        if (repository.findByUsername(request.getUsername()).isPresent())
            throw new UsernameIsOccupiedException("Account with this username is already registered");
    }
}
