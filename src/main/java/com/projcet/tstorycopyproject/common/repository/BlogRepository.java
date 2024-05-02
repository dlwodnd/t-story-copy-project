package com.projcet.tstorycopyproject.common.repository;

import com.projcet.tstorycopyproject.common.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{
}
