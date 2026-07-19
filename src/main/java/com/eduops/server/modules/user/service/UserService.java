package com.eduops.server.modules.user.service;

import com.eduops.server.modules.auth.request.ResetPasswordRequest;
import com.eduops.server.modules.user.entity.User;
import com.eduops.server.modules.user.request.CreateUserRequest;

public interface UserService {

  User findByEmail(String email);

  User findByPhone(String phone);

  void create(CreateUserRequest userData);

  void resetPassword(ResetPasswordRequest request);

}
