package com.projcet.tstorycopyproject.common.repository;


import com.projcet.tstorycopyproject.common.entity.FeedEntity;
import com.projcet.tstorycopyproject.common.entity.FeedFavEntity;
import com.projcet.tstorycopyproject.common.entity.embeddable.FeedFavComposite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFavRepository extends JpaRepository<FeedFavEntity, FeedFavComposite> {
    Long countByFeedEntity(FeedEntity feedEntity);
}
