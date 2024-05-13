package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.FeedCommentEntity;
import com.projcet.tstorycopyproject.global.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCmtRepository extends JpaRepository<FeedCommentEntity, Long> {
    Long countByFeedEntity(FeedEntity feedEntity);
}
