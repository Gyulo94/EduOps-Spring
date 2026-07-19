package com.eduops.server.modules.auth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduops.server.global.api.Api;
import com.eduops.server.global.message.ResponseMessage;
import com.eduops.server.modules.auth.request.AuthRequest;
import com.eduops.server.modules.auth.response.TokenResponse;
import com.eduops.server.modules.auth.service.AuthService;
import com.eduops.server.modules.user.request.CreateUserRequest;
import com.eduops.server.global.utils.CookieProvider;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final CookieProvider cookieProvider;

  @PostMapping("register")
  public Api<Void> register(@Valid @RequestBody CreateUserRequest request) {
    authService.register(request);
    return Api.OK(ResponseMessage.VERIFICATION_EMAIL_SENT);
  }

  @PostMapping("verify")
  public Api<Void> verify(@RequestBody Map<String, String> request) {
    authService.verify(request);
    return Api.OK(ResponseMessage.VERIFICATION_SUCCESS);
  }

  @PostMapping("login")
  public Api<Void> login(@Valid @RequestBody AuthRequest request, HttpServletResponse response) {
    TokenResponse tokens = authService.login(request);
    cookieProvider.setCookies(response, tokens.getAccessToken(), tokens.getRefreshToken());
    return Api.OK();
  }
}