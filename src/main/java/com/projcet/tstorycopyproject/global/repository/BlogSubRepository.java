package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.BlogSubEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.entity.embeddable.BlogSubComposite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogSubRepository extends JpaRepository<BlogSubEntity, BlogSubComposite>{
    List<BlogSubEntity> findAllByUserEntity(UserEntity userEntity);
}
