package com.eduops.server.modules.auth.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eduops.server.global.error.ErrorCode;
import com.eduops.server.global.exception.ApiException;
import com.eduops.server.modules.email.request.UserPayload;
import com.eduops.server.modules.email.service.EmailService;
import com.eduops.server.modules.user.entity.User;
import com.eduops.server.modules.user.request.CreateUserRequest;
import com.eduops.server.modules.user.service.UserService;
import com.eduops.server.redis.RedisKey;
import com.eduops.server.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserService userService;
  private final EmailService emailService;
  private final RedisService redis;
  private final ObjectMapper objectMapper;

  @Override
  public void register(CreateUserRequest request) {
    User user = userService.findByEmail(request.getEmail());
    if (user != null) {
      throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
    }

    User existingPhone = userService.findByPhone(request.getPhone());
    if (existingPhone != null) {
      throw new ApiException(ErrorCode.PHONE_ALREADY_EXISTS);
    }

    UserPayload userRequest = new UserPayload();
    userRequest.setEmail(request.getEmail());
    userRequest.setName(request.getName());
    userRequest.setPhone(request.getPhone());
    userRequest.setPassword(request.getPassword());

    emailService.sendVerificationEmail("register", userRequest);
  }

  @Override
  public void verify(Map<String, String> request) {
    UUID token = UUID.fromString(request.get("token"));
    String type = request.get("type");
    if (token == null) {
      throw new ApiException(ErrorCode.VERIFICATION_TOKEN_INVALID);
    }

    if ("register".equals(type)) {
      verifyRegister(token);
      return;
    } else if ("reset".equals(type)) {
      return;
    } else {
      throw new ApiException(ErrorCode.VERIFICATION_FAILED);
    }
  }

  private void verifyRegister(UUID token) {
    String redisKey = RedisKey.verificationRegister(token);
    String cachedUserData = redis.get(redisKey);
    if (cachedUserData == null) {
      throw new ApiException(ErrorCode.VERIFICATION_TOKEN_INVALID);
    }

    log.info("============================ Verifying cachedUserData: {} ============================", cachedUserData);

    CreateUserRequest userData;
    try {
      userData = objectMapper.readValue(cachedUserData, CreateUserRequest.class);
    } catch (JsonProcessingException e) {
      throw new ApiException(ErrorCode.VERIFICATION_FAILED);
    }
    User user = userService.findByEmail(userData.getEmail());
    if (user != null) {
      redis.del(redisKey);
      throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
    }

    userService.create(userData);

    redis.del(redisKey);
  }
}
