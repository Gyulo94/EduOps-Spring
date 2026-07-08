package com.eduops.server.modules.user.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.eduops.server.modules.user.entity.Role;
import com.eduops.server.modules.user.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private Role role;
  private UserStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
