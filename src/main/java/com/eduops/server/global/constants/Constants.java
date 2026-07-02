package com.eduops.server.global.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class Constants {
  private String appName;
  private String appLogo;
  private String env;
  private String jwtSecretKey;
  private String jwtRefreshSecretKey;
  private long jwtExpiresIn;
  private long jwtRefreshExpiresIn;
  private String clientUrl;
  private String emailUrl;
  private String senderEmail;
  private String senderPwd;
}