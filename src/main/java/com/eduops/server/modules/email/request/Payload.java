package com.eduops.server.modules.email.request;

public sealed interface Payload permits EmailPayload, UserPayload {
  String email();
}