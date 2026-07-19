package com.eduops.server.modules.auth.service;

import java.util.Map;

import com.eduops.server.modules.auth.request.AuthRequest;
import com.eduops.server.modules.auth.response.TokenResponse;
import com.eduops.server.modules.user.request.CreateUserRequest;

public interface AuthService {

  void register(CreateUserRequest request);

  void verify(Map<String, String> request);

  TokenResponse login(AuthRequest request);

}
