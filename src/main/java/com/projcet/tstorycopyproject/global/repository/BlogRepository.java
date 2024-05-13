package com.projcet.tstorycopyproject.global.repository;

import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{
}
