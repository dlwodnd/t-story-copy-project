package com.projcet.tstorycopyproject.common.repository;

import com.projcet.tstorycopyproject.common.entity.FeedEntity;
import com.projcet.tstorycopyproject.common.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashTagEntity, Long> {
    List<HashTagEntity> findTop3ByFeedEntity(FeedEntity feedEntity);
}
