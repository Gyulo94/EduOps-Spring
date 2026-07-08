package com.eduops.server.modules.email.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class EmailPayload implements Payload {
  private String email;

  @Override
  public String email() {
    return email;
  }
}
