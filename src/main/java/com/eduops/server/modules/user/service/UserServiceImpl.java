package com.eduops.server.modules.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.eduops.server.global.error.ErrorCode;
import com.eduops.server.global.exception.ApiException;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.eduops.server.modules.auth.request.ResetPasswordRequest;
import com.eduops.server.modules.user.entity.User;
import com.eduops.server.modules.user.repository.UserRepository;
import com.eduops.server.modules.user.request.CreateUserRequest;
import com.eduops.server.redis.service.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.eduops.server.redis.RedisKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RedisKey redisKey;
  private final RedisService redis;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User findByPhone(String phone) {
    return userRepository.findByPhone(phone);
  }

  @Override
  public void create(CreateUserRequest userData) {
    String hashedPassword = passwordEncoder.encode(userData.getPassword());
    userRepository.save(CreateUserRequest.toEntity(userData, hashedPassword));
  }

  @Override
  public void resetPassword(ResetPasswordRequest request) {
    UUID token = request.getToken();
    String REDIS_KEY = redisKey.verificationReset(token);

    if (token == null || redis.get(REDIS_KEY) == null) {
      throw new ApiException(ErrorCode.VERIFICATION_TOKEN_INVALID);
    }

    String email = request.getEmail();

    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new ApiException(ErrorCode.USER_NOT_FOUND);
    }

    String hashedPassword = passwordEncoder.encode(request.getNewPassword());
    user.setPassword(hashedPassword);
    userRepository.save(user);

    redis.del(REDIS_KEY);
  }

}
