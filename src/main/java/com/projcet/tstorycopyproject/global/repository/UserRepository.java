package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByUserEmail(String email);
}
