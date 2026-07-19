package com.eduops.server.redis;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.eduops.server.global.constants.Constants;

@Component
public class RedisKey {
  private final String PREFIX;

  public RedisKey(Constants constants) {
    this.PREFIX = constants.getAppName();
  }

  public String verificationRegister(UUID token) {
    return PREFIX + ":verification:register:" + token.toString();
  }

  public String verificationReset(UUID token) {
    return PREFIX + ":verification:reset:" + token.toString();
  }

  public String userRefreshToken(UUID userId) {
    return PREFIX + ":RT:" + userId.toString();
  }

  public String cachedTokens(UUID userId) {
    return PREFIX + ":cached:" + userId.toString();
  }
}
