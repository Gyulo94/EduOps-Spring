package com.eduops.server.modules.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduops.server.modules.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

  User findByEmail(String email);

  User findByPhone(String phone);

}
