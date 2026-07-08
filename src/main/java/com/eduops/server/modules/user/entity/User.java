package com.eduops.server.modules.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String name;

  private String email;

  private String password;

  private String phone;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.TEACHER;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private UserStatus status = UserStatus.INACTIVE;

  @Builder.Default
  @Column(name = "joined_at")
  private LocalDateTime joinedAt = LocalDateTime.now();

  @Column(name = "resigned_at", nullable = true)
  private LocalDateTime resignedAt;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Column(name = "employment_status", nullable = false)
  private EmploymentStatus employmentStatus = EmploymentStatus.WORKING;

}
