package com.projcet.tstorycopyproject.page.posts.service;

import com.projcet.tstorycopyproject.page.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.global.entity.*;
import com.projcet.tstorycopyproject.global.entity.embeddable.FeedFavComposite;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.*;
import com.projcet.tstorycopyproject.global.utils.MyFileUtils;
import com.projcet.tstorycopyproject.page.posts.errorcode.PostsErrorCode;
import com.projcet.tstorycopyproject.page.posts.request.FeedInsRq;
import com.projcet.tstorycopyproject.page.posts.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final MyFileUtils myFileUtils;
    private final FeedRepository feedRepository;
    private final BlogRepository blogRepository;
    private final CatRepository catRepository;
    private final FeedFavRepository feedFavRepository;
    private final FeedPicsRepository feedPicsRepository;


    // 카테고리 이름 pk 불러오기
    public List<CatSimpleRp> getCategoryList(Long blogPk, UserEntity userEntity){
        BlogEntity blogEntity = blogRepository.findById(blogPk).orElseThrow(()->new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        return blogEntity.getCategoryEntityList().stream()
                .map(category -> CatSimpleRp.builder()
                        .catPk(category.getCatPk())
                        .catName(category.getCatNm())
                        .build())
                .toList();
    }

    // 비어있는 게시글 생성
    public Void getEmptyFeed(Long blogPk, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(blogPk,userEntity);
        if (feedRepository.findAllByComplete(0L).isPresent()){
        }
        else {
            FeedEntity feedEntity = FeedEntity.builder()
                    .blogEntity(blogEntity)
                    .build();
            feedRepository.save(feedEntity);
        }
        return null;
    }

    // 게시글 수정
    public Void postFeed(FeedInsRq dto, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),userEntity);
        FeedEntity feedEntity = checkUserFeed(dto.getFeedPk(), blogEntity.getBlogPk());
        CategoryEntity catEntity = catRepository.findById(dto.getCatPk())
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_CAT));
        feedEntity.editFeedEntity(dto,catEntity);
        feedRepository.save(feedEntity);
        List<HashTagEntity> hashTagEntityList = new ArrayList<>();
        for(HashTagInfoRp hashTag : dto.getHashTagList()){
            HashTagEntity hashtagEntity = HashTagEntity.builder()
                    .hashTagPk(hashTag.getHashTagPk())
                    .feedEntity(feedEntity)
                    .hashTagNm(hashTag.getHashTagName())
                    .build();
            hashTagEntityList.add(hashtagEntity);
        }
        feedEntity.modifyHashTagEntityList(hashTagEntityList);
        return null;
    }

    // 게시글 삭제
    public Void deleteFeed(String blogAddr, Long feedPk, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        if (!feedEntity.getBlogEntity().getBlogAddress().equals(blogAddr)){
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        feedRepository.delete(feedEntity);
        return null;
    }

    // 게시글 이미지 등록
    public FeedPicInfoRp postFeedPic(Long feedPk, MultipartFile pic, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        String targetPath = "feed/" + feedEntity.getFeedPk();
        String saveFileName = myFileUtils.transferTo(pic, targetPath);
        FeedPicsEntity feedPicsEntity = FeedPicsEntity.builder()
                .feedEntity(feedEntity)
                .feedPic(saveFileName)
                .build();
        feedPicsRepository.save(feedPicsEntity);

        return FeedPicInfoRp.builder()
                .feedPicPk(feedPicsEntity.getFeedPicsPk())
                .picName(saveFileName)
                .build();
    }

    // 게시글 이미지 삭제
    public Void deleteFeedPic(long feedPicPk, UserEntity userEntity){
        FeedPicsEntity feedPicsEntity = feedPicsRepository.findById(feedPicPk)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED_PIC));
        String targetPath = "/feed/" + feedPicsEntity.getFeedEntity().getFeedPk();
        myFileUtils.delFile(targetPath, feedPicsEntity.getFeedPic());
        feedPicsRepository.delete(feedPicsEntity);
        return null;
    }
    // 게시글 수정
    public Void putFeed(FeedInsRq dto, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),userEntity);
        FeedEntity feedEntity = checkUserFeed(dto.getFeedPk(), blogEntity.getBlogPk());
        CategoryEntity catEntity = catRepository.findById(dto.getCatPk())
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_CAT));
        feedEntity.editFeedEntity(dto,catEntity);
        feedRepository.save(feedEntity);
        List<HashTagEntity> hashTagEntityList = new ArrayList<>();
        for(HashTagInfoRp hashTag : dto.getHashTagList()){
            HashTagEntity hashtagEntity = HashTagEntity.builder()
                    .hashTagPk(hashTag.getHashTagPk())
                    .feedEntity(feedEntity)
                    .hashTagNm(hashTag.getHashTagName())
                    .build();
            hashTagEntityList.add(hashtagEntity);
        }
        feedEntity.modifyHashTagEntityList(hashTagEntityList);

        return null;
    }
    // 피드 좋아요
    public Void patchFeedFav(long feedPk, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        FeedFavComposite feedFavComposite = FeedFavComposite.builder()
                .feedPk(feedEntity.getFeedPk())
                .userPk(userEntity.getUserPk())
                .build();
        FeedFavEntity feedFavEntity = feedFavRepository.findById(feedFavComposite)
                .orElse(null);
        if (feedFavEntity == null){
            feedFavEntity = FeedFavEntity.builder()
                    .feedFavComposite(feedFavComposite)
                    .feedEntity(feedEntity)
                    .userEntity(userEntity)
                    .build();
            feedFavRepository.save(feedFavEntity);
        }
        else {
            feedFavRepository.delete(feedFavEntity);
        }
        return null;

    }

    // 게시글 임시 저장
    public Void postTemporaryFeed(FeedInsRq dto, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),userEntity);
        FeedEntity feedEntity = checkUserFeed(dto.getFeedPk(), blogEntity.getBlogPk());
        CategoryEntity catEntity = catRepository.findById(dto.getCatPk())
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_CAT));
        feedEntity.temporaryFeedEntity(dto,catEntity);
        feedRepository.save(feedEntity);
        List<HashTagEntity> hashTagEntityList = new ArrayList<>();
        for(HashTagInfoRp hashTag : dto.getHashTagList()){
            HashTagEntity hashtagEntity = HashTagEntity.builder()
                    .hashTagPk(hashTag.getHashTagPk())
                    .feedEntity(feedEntity)
                    .hashTagNm(hashTag.getHashTagName())
                    .build();
            hashTagEntityList.add(hashtagEntity);
        }
        feedEntity.modifyHashTagEntityList(hashTagEntityList);
        return null;
    }

    // 임시 저장 게시글 리스트
    public TemporaryFeedListRp getTemporaryFeedList(String blogAddr , Pageable pageable, UserEntity userEntity){
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddr)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        Page<FeedEntity> feedEntities = feedRepository.findAllByBlogEntityAndComplete(blogEntity,2L,pageable);
        List<TemporaryFeed> temporaryFeedList = feedEntities.map(feedEntity -> TemporaryFeed.builder()
                .feedPk(feedEntity.getFeedPk())
                .title(feedEntity.getTitle())
                .createdAt(feedEntity.getCreatedAt())
                .build()).toList();
        return TemporaryFeedListRp.builder()
                .temporaryFeedList(temporaryFeedList)
                .totalPage(feedEntities.getTotalPages())
                .build();
    }

    // 임시 저장 게시글 삭제
    public Void deleteTemporaryFeed(long feedPk, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findByFeedPkAndComplete(feedPk,2L)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        if (!feedEntity.getBlogEntity().getUserEntity().equals(userEntity)){
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        feedRepository.delete(feedEntity);
        return null;
    }

    // 임시 저장 게시글 불러오기
    public TemporaryFeedRp getTemporaryFeed(long feedPk, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findByFeedPkAndComplete(feedPk,2L)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        if (!feedEntity.getBlogEntity().getUserEntity().equals(userEntity)){
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        List<HashTagInfoRp> hashTagInfoVoList = feedEntity.getHashTagEntitySet().stream()
                .map(hashTagEntity -> HashTagInfoRp.builder()
                        .hashTagPk(hashTagEntity.getHashTagPk())
                        .hashTagName(hashTagEntity.getHashTagNm())
                        .build())
                .toList();
        return TemporaryFeedRp.builder()
                .feedPk(feedEntity.getFeedPk())
                .title(feedEntity.getTitle())
                .content(feedEntity.getContents())
                .categoryInfo(FeedCategoryInfo.builder()
                        .categoryPk(feedEntity.getCategoryEntity().getCatPk())
                        .categoryName(feedEntity.getCategoryEntity().getCatNm())
                        .build())
                .hashTagInfoList(hashTagInfoVoList)
                .build();
    }



    private FeedEntity checkUserFeed(long feedPk, long blogPk){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(PostsErrorCode.NOT_FOUND_FEED));
        if (!feedEntity.getBlogEntity().getBlogPk().equals(blogPk)){
            throw new CustomException(BlogErrorCode.NOT_MATCH_USER);
        }
        return feedEntity;
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
