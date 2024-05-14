package com.projcet.tstorycopyproject.domain.blog.service;

import com.projcet.tstorycopyproject.domain.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.domain.blog.request.BlogModifyRq;
import com.projcet.tstorycopyproject.domain.blog.request.BlogRegisterRq;
import com.projcet.tstorycopyproject.domain.blog.request.CategoryInfoRq;
import com.projcet.tstorycopyproject.domain.blog.request.CategoryInsRq;
import com.projcet.tstorycopyproject.domain.blog.response.BlogInfoRp;
import com.projcet.tstorycopyproject.domain.blog.response.CategoryInfoRp;
import com.projcet.tstorycopyproject.domain.blog.response.CategorySubInfoRp;
import com.projcet.tstorycopyproject.domain.catagory.errorcode.CatErrorCode;
import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.entity.BlogSubEntity;
import com.projcet.tstorycopyproject.global.entity.CategoryEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.entity.embeddable.BlogSubComposite;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.BlogRepository;
import com.projcet.tstorycopyproject.global.repository.BlogSubRepository;
import com.projcet.tstorycopyproject.global.repository.CatRepository;
import com.projcet.tstorycopyproject.global.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BlogService {
    private final MyFileUtils myFileUtils;
    private final BlogRepository blogRepository;
    private final CatRepository catRepository;
    private final BlogSubRepository blogSubRepository;

    //블로그 등록
    public BlogInfoRp registerBlog(BlogRegisterRq dto, MultipartFile blogProfileImg, UserEntity user){
        BlogEntity blogEntity = BlogEntity.builder()
                .userEntity(user)
                .blogTitle(dto.getBlogTitle())
                .blogAddress(dto.getBlogAddress())
                .blogNickname(dto.getBlogNickname())
                .build();
        blogRepository.saveAndFlush(blogEntity);
        String target = "/blog/" + blogEntity.getBlogPk();
        String fileNm = myFileUtils.transferTo(blogProfileImg, target);
        blogEntity.changeBlogPic(fileNm);

        return BlogInfoRp.builder()
                .blogPk(blogEntity.getBlogPk())
                .blogInfo(blogEntity.getBlogInfo())
                .blogTitle(blogEntity.getBlogTitle())
                .blogAddress(blogEntity.getBlogAddress())
                .blogPic(blogEntity.getBlogPic())
                .blogNickname(blogEntity.getBlogNickname())
                .build();
    }
    // 블로그 수정
    public BlogInfoRp modifyBlog(BlogModifyRq dto, UserEntity user) {
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),user);
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
    // 블로그 삭제
    public void closeBlog(long blogPk,UserEntity user){
        BlogEntity blogEntity = checkUserBlog(blogPk,user);
        blogRepository.delete(blogEntity);
    }

    //카테로리 등록 및 수정
    public List<CategoryInfoRp> registerCategory(CategoryInsRq dto, UserEntity user){
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),user);
        CategoryEntity topCatEntity = null;
        List<CategoryEntity> catEntityList = new ArrayList<>();
        for(CategoryInfoRq catInfo : dto.getCatInfoList()){
            topCatEntity = null;
            if (catInfo.getTopCatSeq() != null && catInfo.getTopCatSeq() != 0){
                topCatEntity = catRepository.findBySeq(catInfo.getTopCatSeq())
                        .orElseThrow(() -> new CustomException(CatErrorCode.NOT_FOUND_TOP_CAT));
                if (topCatEntity.getCategoryEntity() != null){
                    throw new CustomException(CatErrorCode.ALREADY_SUB_CAT);
                }
            }
            if (catInfo.getCatPk() != null && catInfo.getCatPk() != 0){
                CategoryEntity catEntity = catRepository.findById(catInfo.getCatPk())
                        .orElseThrow(() -> new CustomException(CatErrorCode.NOT_FOUND_CAT));
                if (!catEntity.getBlogEntity().equals(blogEntity)){
                    throw new CustomException(CatErrorCode.NOT_MATCH_BLOG);
                }
                catEntity.editCatEntity(catInfo, topCatEntity);
                catEntityList.add(catEntity);
            }

        }
        blogEntity.modifyCategoryEntityList(catEntityList);
        blogRepository.saveAndFlush(blogEntity);

        for(CategoryInfoRq catInfo : dto.getCatInfoList()){
            topCatEntity = null;
            if (catInfo.getCatPk() != null && catInfo.getCatPk() != 0){
                continue;
            }
            if (catInfo.getTopCatSeq() != null && catInfo.getTopCatSeq() != 0){
                topCatEntity = catRepository.findBySeq(catInfo.getTopCatSeq())
                        .orElseThrow(() -> new CustomException(CatErrorCode.NOT_FOUND_TOP_CAT));
                if (topCatEntity.getCategoryEntity() != null){
                    throw new CustomException(CatErrorCode.ALREADY_SUB_CAT);
                }
            }
            CategoryEntity existingCatEntity = catRepository.findByCategoryEntityAndCatOrder(topCatEntity, catInfo.getCatOrder());
            if (existingCatEntity != null) {
                throw new CustomException(CatErrorCode.DUPLICATE_CAT_ORDER);
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
    // 비로그인 유저 대글 허용
    public Void cmtAccess(long blogPk,UserEntity user){
        BlogEntity blogEntity = checkUserBlog(blogPk,user);
        return blogEntity.cmtAccess();
    }

    //블로그 구독
    public void subscribe(long blogPk,UserEntity user){
        BlogEntity blogEntity = blogRepository.findById(blogPk)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        BlogSubComposite blogSubComposite = BlogSubComposite.builder()
                .blogPk(blogPk)
                .userPk(user.getUserPk())
                .build();
        if (blogSubRepository.findById(blogSubComposite).isPresent()){
            blogSubRepository.deleteById(blogSubComposite);
        }
        else {
            BlogSubEntity blogSubEntity = BlogSubEntity.builder()
                    .blogSubComposite(blogSubComposite)
                    .blogEntity(blogEntity)
                    .userEntity(user)
                    .build();
            blogSubRepository.save(blogSubEntity);
        }
    }





    private BlogEntity checkUserBlog(long blogPk,UserEntity userEntity){
        BlogEntity blogEntity = blogRepository.findById(blogPk)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        if (!blogEntity.getUserEntity().equals(userEntity)){
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        return blogEntity;
    }

}
