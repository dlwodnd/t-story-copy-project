package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{

    Optional<BlogEntity> findByBlogAddress(String blogAddress);

    Optional<BlogEntity> findByBlogAddressAndUserEntity(String blogAddr, UserEntity user);
}
