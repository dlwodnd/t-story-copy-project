package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.FeedCommentEntity;
import com.projcet.tstorycopyproject.global.entity.FeedEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedCmtRepository extends JpaRepository<FeedCommentEntity, Long> {
    Long countByFeedEntity(FeedEntity feedEntity);

    Optional<FeedCommentEntity> findByCmtPkAndUserEntity(long cmtPk, UserEntity userEntity);
}
