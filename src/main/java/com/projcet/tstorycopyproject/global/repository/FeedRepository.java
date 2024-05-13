package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.entity.CategoryEntity;
import com.projcet.tstorycopyproject.global.entity.FeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    Long countByCategoryEntity(CategoryEntity categoryEntity);
    Long countAllByBlogEntity(BlogEntity blogEntity);
    Optional<FeedEntity> findAllByComplete(Long complete);
    Page<FeedEntity> findAllByCompleteAndFeedPrivateOrderByCreatedAtDesc(Long complete, Long feedPrivate, Pageable pageable);
    Page<FeedEntity> findAllByCompleteAndFeedPrivateAndBlogEntityInOrderByCreatedAtDesc(Long complete, Long feedPrivate, List<BlogEntity> blogEntityList, Pageable pageable);
}
