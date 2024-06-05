package com.projcet.tstorycopyproject.page.manage.service;

import com.projcet.tstorycopyproject.page.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.entity.CategoryEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.BlogRepository;
import com.projcet.tstorycopyproject.global.repository.CatRepository;
import com.projcet.tstorycopyproject.page.manage.errorcode.ManageErrorCode;
import com.projcet.tstorycopyproject.page.manage.request.BlogModifyRq;
import com.projcet.tstorycopyproject.page.manage.request.CategoryInfoRq;
import com.projcet.tstorycopyproject.page.manage.response.CategoryInfoRp;
import com.projcet.tstorycopyproject.page.manage.response.CategorySubInfoRp;
import com.projcet.tstorycopyproject.page.member.response.BlogInfoRp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageService {
    private final BlogRepository blogRepository;
    private final CatRepository catRepository;

    // 카테고리 관리
    public List<CategoryInfoRp> registerCategory(String blogAdd , List<CategoryInfoRq> catInfoList, UserEntity user) {
        BlogEntity blogEntity = checkUserBlog(blogAdd, user);
        CategoryEntity topCatEntity = null;
        List<CategoryEntity> catEntityList = new ArrayList<>();
        for (CategoryInfoRq catInfo : catInfoList) {
            if (catInfo.getTopCatSeq() != null && catInfo.getTopCatSeq() != 0) {
                topCatEntity = catRepository.findBySeq(catInfo.getTopCatSeq())
                        .orElseThrow(() -> new CustomException(ManageErrorCode.NOT_FOUND_TOP_CAT));
                if (topCatEntity.getCategoryEntity() != null) {
                    throw new CustomException(ManageErrorCode.ALREADY_SUB_CAT);
                }
            }
            if (catInfo.getCatPk() != null && catInfo.getCatPk() != 0) {
                CategoryEntity catEntity = catRepository.findById(catInfo.getCatPk())
                        .orElseThrow(() -> new CustomException(ManageErrorCode.NOT_FOUND_CAT));
                if (!catEntity.getBlogEntity().equals(blogEntity)) {
                    throw new CustomException(ManageErrorCode.NOT_MATCH_BLOG);
                }
                catEntity.editCatEntity(catInfo, topCatEntity);
                catEntityList.add(catEntity);
            }

        }
        blogEntity.modifyCategoryEntityList(catEntityList);
        blogRepository.saveAndFlush(blogEntity);

        for (CategoryInfoRq catInfo : catInfoList) {
            topCatEntity = null;
            if (catInfo.getCatPk() != null && catInfo.getCatPk() != 0) {
                continue;
            }
            if (catInfo.getTopCatSeq() != null && catInfo.getTopCatSeq() != 0) {
                topCatEntity = catRepository.findBySeq(catInfo.getTopCatSeq())
                        .orElseThrow(() -> new CustomException(ManageErrorCode.NOT_FOUND_TOP_CAT));
                if (topCatEntity.getCategoryEntity() != null) {
                    throw new CustomException(ManageErrorCode.ALREADY_SUB_CAT);
                }
            }
            CategoryEntity existingCatEntity = catRepository.findByCategoryEntityAndCatOrder(topCatEntity, catInfo.getCatOrder());
            if (existingCatEntity != null) {
                throw new CustomException(ManageErrorCode.DUPLICATE_CAT_ORDER);
            }
            CategoryEntity catEntity = CategoryEntity.builder()
                    .blogEntity(blogEntity)
                    .seq(catInfo.getCatSeq())
                    .catNm(catInfo.getCatName())
                    .categoryEntity(topCatEntity)
                    .catOrder(catInfo.getCatOrder())
                    .build();
            catEntityList.add(catEntity);
        }
        catRepository.saveAll(catEntityList);


        return catEntityList.stream()
                .filter(catEntity -> catEntity.getCategoryEntity() == null)
                .map(catEntity -> CategoryInfoRp.builder()
                        .catPk(catEntity.getCatPk())
                        .catName(catEntity.getCatNm())
                        .catOrder(catEntity.getCatOrder())
                        .catSeq(catEntity.getSeq())
                        .categorySubInfoRpList(catEntity.getCategoryEntityList() == null || catEntity.getCategoryEntityList().isEmpty() ? null :
                                catEntity.getCategoryEntityList().stream()
                                        .map(catSubEntity -> CategorySubInfoRp.builder()
                                                .catPk(catSubEntity.getCatPk())
                                                .catName(catSubEntity.getCatNm())
                                                .catOrder(catSubEntity.getCatOrder())
                                                .catSeq(catSubEntity.getSeq())
                                                .topSeq(catSubEntity.getCategoryEntity().getSeq())
                                                .build())
                                        .toList())
                        .build())
                .toList();
    }
    // 블로그 정보 수정
    public BlogInfoRp modifyBlog(String blogAdd, BlogModifyRq dto, UserEntity user) {
        BlogEntity blogEntity = checkUserBlog(blogAdd,user);
        blogEntity.modifyBlogInfo(dto);

        return BlogInfoRp.builder()
                .blogPk(blogEntity.getBlogPk())
                .blogInfo(blogEntity.getBlogInfo())
                .blogTitle(blogEntity.getBlogTitle())
                .blogAddress(blogEntity.getBlogAddress())
                .blogPic(blogEntity.getBlogPic())
                .blogNickname(blogEntity.getBlogNickname())
                .build();
    }


    private BlogEntity checkUserBlog(String blogAdd, UserEntity userEntity) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAdd)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        if (!blogEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        return blogEntity;
    }


}
