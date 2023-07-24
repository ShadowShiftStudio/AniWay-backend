package com.shadowshiftstudio.aniway.services;

import com.shadowshiftstudio.aniway.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
