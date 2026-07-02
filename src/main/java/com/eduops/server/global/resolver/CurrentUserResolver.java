package com.eduops.server.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.eduops.server.global.annotations.CurrentUser;
import com.eduops.server.global.error.ErrorCode;
import com.eduops.server.global.exception.ApiException;
import com.eduops.server.global.jwt.JwtPayload;

@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

  @Override
  @SuppressWarnings("null")
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasCurrentUserAnnotation = parameter.hasParameterAnnotation(CurrentUser.class);
    boolean isSupportedType = JwtPayload.class.isAssignableFrom(parameter.getParameterType());
    return hasCurrentUserAnnotation && isSupportedType;
  }

  @Override
  @SuppressWarnings("null")
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !(authentication.getPrincipal() instanceof JwtPayload)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED);
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof JwtPayload jwtPayload) {
      return jwtPayload;
    }

    throw new ApiException(ErrorCode.UNAUTHORIZED);
  }
}