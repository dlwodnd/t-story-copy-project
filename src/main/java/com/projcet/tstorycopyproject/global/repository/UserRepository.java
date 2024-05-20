package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.entity.jpa_enum.SocialEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByUserEmail(String email);
    Optional<UserEntity> findByUserEmailAndSocialType(String email, SocialEnum socialEnum);
}
