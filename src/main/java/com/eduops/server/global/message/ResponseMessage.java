package com.eduops.server.global.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage implements ResponseMessageInterface {
  VERIFICATION_EMAIL_SENT("인증 이메일이 발송되었습니다. 도착하지 않았다면, 스팸 메일함을 확인해주세요."),
  VERIFICATION_SUCCESS("이메일 인증이 성공적으로 완료되었습니다.");

  private final String message;
}
