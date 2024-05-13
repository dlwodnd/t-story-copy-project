package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.FeedEntity;
import com.projcet.tstorycopyproject.global.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashTagEntity, Long> {
    List<HashTagEntity> findTop3ByFeedEntity(FeedEntity feedEntity);
}
