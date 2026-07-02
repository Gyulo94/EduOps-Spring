package com.eduops.server.global.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import com.eduops.server.global.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final Constants constants;

  private SecretKey getSigningKey(String secretKeyBase64) {
    return Keys.hmacShaKeyFor(secretKeyBase64.getBytes(StandardCharsets.UTF_8));
  }

  public String createAccessToken(UUID id, String role) {
    Instant now = Instant.now();
    Instant expiryInstant = now.plus(Duration.ofSeconds(constants.getJwtExpiresIn()));
    Date expiredAt = Date.from(expiryInstant);

    return Jwts.builder()
        .signWith(getSigningKey(constants.getJwtSecretKey()), SignatureAlgorithm.HS256)
        .setSubject(id.toString())
        .claim("role", role)
        .setIssuedAt(Date.from(now))
        .setExpiration(expiredAt)
        .compact();
  }

  public String createRefreshToken(UUID id) {
    Instant now = Instant.now();
    Instant expiryInstant = now.plus(Duration.ofSeconds(constants.getJwtRefreshExpiresIn()));
    Date expiredAt = Date.from(expiryInstant);

    return Jwts.builder()
        .signWith(getSigningKey(constants.getJwtRefreshSecretKey()), SignatureAlgorithm.HS256)
        .setSubject(id.toString())
        .setIssuedAt(Date.from(now))
        .setExpiration(expiredAt)
        .compact();
  }

  public Claims validateAccessToken(String accessToken) {
    return parseClaims(accessToken, getSigningKey(constants.getJwtSecretKey()), "액세스 토큰");
  }

  public Claims validateRefreshToken(String refreshToken) {
    return parseClaims(refreshToken, getSigningKey(constants.getJwtRefreshSecretKey()), "리프레시 토큰");
  }

  private Claims parseClaims(String token, SecretKey signingKey, String tokenType) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      log.warn("만료된 {}입니다.", tokenType, e);
    } catch (UnsupportedJwtException e) {
      log.warn("지원되지 않는 {}입니다.", tokenType, e);
    } catch (MalformedJwtException e) {
      log.warn("형식 오류 또는 잘못된 구성의 {}입니다.", tokenType, e);
    } catch (SignatureException e) {
      log.warn("유효하지 않은 {} 서명입니다.", tokenType, e);
    } catch (IllegalArgumentException e) {
      log.warn("유효하지 않거나 비어있는 {}입니다.", tokenType, e);
    } catch (Exception e) {
      log.error("{} 검증 중 알 수 없는 오류 발생.", tokenType, e);
    }
    return null;
  }
}