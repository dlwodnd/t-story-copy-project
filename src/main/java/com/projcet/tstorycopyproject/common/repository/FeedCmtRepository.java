package com.projcet.tstorycopyproject.common.repository;

import com.projcet.tstorycopyproject.common.entity.FeedCommentEntity;
import com.projcet.tstorycopyproject.common.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCmtRepository extends JpaRepository<FeedCommentEntity, Long> {
    Long countByFeedEntity(FeedEntity feedEntity);
}
