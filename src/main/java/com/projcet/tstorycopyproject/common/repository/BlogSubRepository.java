package com.projcet.tstorycopyproject.common.repository;

import com.projcet.tstorycopyproject.common.entity.BlogSubEntity;
import com.projcet.tstorycopyproject.common.entity.UserEntity;
import com.projcet.tstorycopyproject.common.entity.embeddable.BlogSubComposite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogSubRepository extends JpaRepository<BlogSubEntity, BlogSubComposite>{
    List<BlogSubEntity> findAllByUserEntity(UserEntity userEntity);
}
