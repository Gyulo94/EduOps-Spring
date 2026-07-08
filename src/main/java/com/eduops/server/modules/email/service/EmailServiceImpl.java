package com.eduops.server.modules.email.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.eduops.server.global.constants.Constants;
import com.eduops.server.global.error.ErrorCode;
import com.eduops.server.global.exception.ApiException;
import com.eduops.server.modules.email.request.EmailRequest;
import com.eduops.server.modules.email.request.Payload;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;

import com.eduops.server.redis.RedisKey;
import com.eduops.server.redis.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Async("emailAsyncExecutor")
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final RedisService redis;
  private final Constants constants;
  @Qualifier("emailWebClient")
  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  public void sendVerificationEmail(String type, Payload payload) {

    UUID token = UUID.randomUUID();

    String redisKey;

    if (type.equals("register")) {
      redisKey = RedisKey.verificationRegister(token);
    } else if (type.equals("reset")) {
      redisKey = RedisKey.verificationReset(token);
    } else {
      throw new ApiException(ErrorCode.BAD_REQUEST);
    }

    String jsonPayload;
    try {
      jsonPayload = objectMapper.writeValueAsString(payload);
    } catch (Exception e) {
      throw new ApiException(ErrorCode.SERVER_ERROR);
    }

    redis.set(redisKey, jsonPayload, 900L);

    String path = type.equals("register") ? "register/verify" : "reset-password/verify";
    String url = constants.getClientUrl() + "/" + path + "?token=" + token.toString();

    EmailRequest emailRequest = new EmailRequest(
        constants.getAppName(),
        constants.getAppLogo(),
        constants.getSenderEmail(),
        constants.getSenderPwd(),
        payload.email(),
        type,
        url);

    try {
      webClient.post()
          .bodyValue(emailRequest)
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (Exception e) {
      throw new ApiException(ErrorCode.SERVER_ERROR);
    }
  }
}
