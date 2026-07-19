package com.eduops.server.modules.auth.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
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
public class ResetPasswordRequest {

  @NotBlank()
  private String email;

  @NotBlank()
  private UUID token;

  @NotBlank()
  private String newPassword;
}
