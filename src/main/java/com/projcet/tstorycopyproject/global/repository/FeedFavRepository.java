package com.projcet.tstorycopyproject.global.repository;


import com.projcet.tstorycopyproject.global.entity.FeedEntity;
import com.projcet.tstorycopyproject.global.entity.FeedFavEntity;
import com.projcet.tstorycopyproject.global.entity.embeddable.FeedFavComposite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFavRepository extends JpaRepository<FeedFavEntity, FeedFavComposite> {
    Long countByFeedEntity(FeedEntity feedEntity);
}
