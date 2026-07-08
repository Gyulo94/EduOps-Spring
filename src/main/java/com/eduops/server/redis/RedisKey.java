package com.eduops.server.redis;

import java.util.UUID;

public class RedisKey {
  public static String verificationRegister(UUID token) {
    return "verification:register:" + token.toString();
  }

  public static String verificationReset(UUID token) {
    return "verification:reset:" + token.toString();
  }
}
