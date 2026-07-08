package com.eduops.server.modules.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduops.server.modules.user.entity.User;
import com.eduops.server.modules.user.repository.UserRepository;
import com.eduops.server.modules.user.request.CreateUserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User findByPhone(String phone) {
    return userRepository.findByPhone(phone);
  }

  @SuppressWarnings("null")
  @Override
  public void create(CreateUserRequest userData) {
    log.info("============================ Creating user with email: {} ============================",
        userData.getEmail());
    String hashedPassword = passwordEncoder.encode(userData.getPassword());
    userRepository.save(CreateUserRequest.toEntity(userData, hashedPassword));
  }

}
