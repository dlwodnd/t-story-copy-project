package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatRepository extends JpaRepository<CategoryEntity, Long>{
    Optional<CategoryEntity> findBySeq(long seq);
    CategoryEntity findByCategoryEntityAndCatOrder(CategoryEntity categoryEntity, long catOrder);
}
