package com.eduops.server.modules.user.request;

import com.eduops.server.modules.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateUserRequest {

  @NotBlank()
  private String email;

  @NotBlank()
  private String name;

  @NotBlank()
  private String phone;

  @NotBlank()
  private String password;

  public static User toEntity(CreateUserRequest request, String hashedPassword) {
    return User.builder()
        .email(request.getEmail())
        .name(request.getName())
        .phone(request.getPhone())
        .password(hashedPassword)
        .build();
  }
}
