package com.eduops.server.global.jwt;

import java.util.UUID;
import com.eduops.server.modules.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPayload {
  private UUID id;
  private Role role;
}
