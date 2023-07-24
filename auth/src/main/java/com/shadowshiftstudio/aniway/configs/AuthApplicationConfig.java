package com.shadowshiftstudio.aniway.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaRepositories(basePackages = "com.shadowshiftstudio.aniway")
@RequiredArgsConstructor
public class AuthApplicationConfig {}
