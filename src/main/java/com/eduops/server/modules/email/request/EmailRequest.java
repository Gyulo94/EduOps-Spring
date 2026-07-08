package com.eduops.server.modules.email.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRequest {
  private String serviceName;
  private String logo;
  private String senderEmail;
  private String senderPwd;
  private String email;
  private String type;
  private String url;

}
