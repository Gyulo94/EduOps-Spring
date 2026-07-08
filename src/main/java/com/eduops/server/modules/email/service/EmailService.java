package com.eduops.server.modules.email.service;

import com.eduops.server.modules.email.request.Payload;

public interface EmailService {
  void sendVerificationEmail(String type, Payload payload);
}
