package com.projcet.tstorycopyproject.domain.catagory.service;

import com.projcet.tstorycopyproject.domain.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.domain.feed.response.CatFeedInfoVo;
import com.projcet.tstorycopyproject.domain.feed.response.CatSubFeedInfoVo;
import com.projcet.tstorycopyproject.domain.feed.response.CatVo;
import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.BlogRepository;
import com.projcet.tstorycopyproject.global.repository.CatRepository;
import com.projcet.tstorycopyproject.global.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final BlogRepository blogRepository;
    private final FeedRepository feedRepository;
    //카테고리 리스트 불러오기
    public CatVo getCategory(long blogPk){
        BlogEntity blogEntity = blogRepository.findById(blogPk).orElseThrow(()->new CustomException(BlogErrorCode.NOT_FOUND_BLOG));

        List<CatFeedInfoVo> catFeedInfoVoList = blogEntity.getCategoryEntityList().stream()
                .map(category -> {
                    long feedCount = feedRepository.countByCategoryEntity(category);
                    if (!category.getCategoryEntityList().isEmpty()){
                        feedCount = feedCount + category.getCategoryEntityList().stream()
                                .mapToLong(feedRepository::countByCategoryEntity)
                                .sum();
                    }
                    return CatFeedInfoVo.builder()
                            .catPk(category.getCatPk())
                            .catName(category.getCatNm())
                            .feedCount(feedCount)
                            .catSubInfoVoList(category.getCategoryEntityList() == null || category.getCategoryEntityList().isEmpty() ? null :
                                    category.getCategoryEntityList().stream()
                                            .map(subCatEntity -> {
                                                long subFeedCount = feedRepository.countByCategoryEntity(subCatEntity);
                                                return CatSubFeedInfoVo.builder()
                                                        .catPk(subCatEntity.getCatPk())
                                                        .catName(subCatEntity.getCatNm())
                                                        .topSeq(subCatEntity.getCategoryEntity().getSeq())
                                                        .feedCount(subFeedCount)
                                                        .build();
                                            })
                                            .toList()
                            ).build();
                })
                .toList();
        return CatVo.builder()
                .catAll("전체")
                .feedCount(feedRepository.countAllByBlogEntity(blogEntity))
                .catFeedInfoVoList(catFeedInfoVoList)
                .build();
    }
}
