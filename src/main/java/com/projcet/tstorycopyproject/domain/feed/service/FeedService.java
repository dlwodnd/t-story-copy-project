package com.projcet.tstorycopyproject.domain.feed.service;


import com.projcet.tstorycopyproject.domain.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.domain.feed.errorcode.FeedErrorCode;
import com.projcet.tstorycopyproject.domain.feed.request.FeedCmtInsDto;
import com.projcet.tstorycopyproject.domain.feed.request.FeedCmtPutDto;
import com.projcet.tstorycopyproject.domain.feed.request.FeedInsDto;
import com.projcet.tstorycopyproject.domain.feed.response.*;
import com.projcet.tstorycopyproject.global.Const;
import com.projcet.tstorycopyproject.global.entity.*;
import com.projcet.tstorycopyproject.global.entity.embeddable.FeedFavComposite;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.*;
import com.projcet.tstorycopyproject.global.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final MyFileUtils myFileUtils;
    private final FeedRepository feedRepository;
    private final BlogRepository blogRepository;
    private final CatRepository catRepository;
    private final FeedFavRepository feedFavRepository;
    private final FeedCmtRepository feedCmtRepository;
    private final HashtagRepository hashtagRepository;
    private final FeedPicsRepository feedPicsRepository;
    private final BlogSubRepository blogSubRepository;

    //카테고리 이름 pk 불러오기
    public List<CatSimpleVo> getCategoryList(Long blogPk, UserEntity userEntity){
        BlogEntity blogEntity = blogRepository.findById(blogPk).orElseThrow(()->new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        return blogEntity.getCategoryEntityList().stream()
                .map(category -> CatSimpleVo.builder()
                        .catPk(category.getCatPk())
                        .catName(category.getCatNm())
                        .build())
                .toList();
    }

    //비어있는 피드 생성
    public void getEmptyFeed(Long blogPk, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(blogPk,userEntity);
        if (feedRepository.findAllByComplete(0L).isPresent()){
        }
        else {
            FeedEntity feedEntity = FeedEntity.builder()
                    .blogEntity(blogEntity)
                    .build();
            feedRepository.save(feedEntity);
        }
    }

    //피드 삭제
    public void deleteFeed(long feedPk, long blogPk, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(blogPk,userEntity);
        FeedEntity feedEntity = checkUserFeed(feedPk,blogEntity.getBlogPk());
        String targetPath = "/feed/" + feedEntity.getFeedPk();
        myFileUtils.delFiles(targetPath);
        feedRepository.delete(feedEntity);
    }
    //피드 사진 등록
    public FeedPicInfoVo postFeedPic(Long feedPk, MultipartFile pic, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED));
        String targetPath = "feed/" + feedEntity.getFeedPk();
        String saveFileName = myFileUtils.transferTo(pic, targetPath);
        FeedPicsEntity feedPicsEntity = FeedPicsEntity.builder()
                .feedEntity(feedEntity)
                .feedPic(saveFileName)
                .build();
        feedPicsRepository.save(feedPicsEntity);

        return FeedPicInfoVo.builder()
                .feedPicPk(feedPicsEntity.getFeedPicsPk())
                .picName(saveFileName)
                .build();
    }
    //피드 사진 삭제
    public void deleteFeedPic(long feedPicPk, UserEntity userEntity){
        FeedPicsEntity feedPicsEntity = feedPicsRepository.findById(feedPicPk)
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED_PIC));
        String targetPath = "/feed/" + feedPicsEntity.getFeedEntity().getFeedPk();
        myFileUtils.delFile(targetPath, feedPicsEntity.getFeedPic());
        feedPicsRepository.delete(feedPicsEntity);
    }
    //피드 수정
    public void putFeed(FeedInsDto dto, UserEntity userEntity){
        BlogEntity blogEntity = checkUserBlog(dto.getBlogPk(),userEntity);
        FeedEntity feedEntity = checkUserFeed(dto.getFeedPk(), blogEntity.getBlogPk());
        CategoryEntity catEntity = catRepository.findById(dto.getCatPk())
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_CAT));
        feedEntity.editFeedEntity(dto,catEntity);
        feedRepository.save(feedEntity);
        List<HashTagEntity> hashTagEntityList = new ArrayList<>();
        for(HashTagInfoVo hashTag : dto.getHashTagList()){
            HashTagEntity hashtagEntity = HashTagEntity.builder()
                    .hashTagPk(hashTag.getHashTagPk())
                    .feedEntity(feedEntity)
                    .hashTagNm(hashTag.getHashTagName())
                    .build();
            hashTagEntityList.add(hashtagEntity);
        }
        feedEntity.modifyHashTagEntityList(hashTagEntityList);
    }
    //피드 좋아요
    public void patchFeedFav(long feedPk, UserEntity userEntity){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED));
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

    }
    //피드 댓글 등록
    public void postFeedCmt(FeedCmtInsDto dto, UserEntity userEntity) {
        FeedEntity feedEntity = feedRepository.findById(dto.getFeedPk())
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED));
        FeedCommentEntity feedCommentEntity = FeedCommentEntity.builder()
                .feedEntity(feedEntity)
                .userEntity(userEntity)
                .cmt(dto.getCmt())
                .cmtPrivate(dto.getCmtPrivate())
                .build();
        feedCmtRepository.save(feedCommentEntity);
    }
    //피드 댓글 수정
    public void putFeedCmt(FeedCmtPutDto dto, UserEntity userEntity){
        FeedCommentEntity feedCommentEntity = feedCmtRepository.findById(dto.getCmtPk())
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED_CMT));
        if (userEntity == null){
            if (!feedCommentEntity.getCmtPw().equals(dto.getCmtPw())){
                throw new CustomException(FeedErrorCode.MISS_MATCH_PW);
            }
        }
        feedCommentEntity.modifyFeedComment(dto);
        feedCmtRepository.save(feedCommentEntity);
    }
    //댓글 삭제
    public void deleteFeedCmt(long cmtPk , String cmtPw, UserEntity userEntity){
        FeedCommentEntity feedCommentEntity = feedCmtRepository.findById(cmtPk)
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED_CMT));

        if (userEntity == null){
            if (!feedCommentEntity.getCmtPw().equals(cmtPw)){
                throw new CustomException(FeedErrorCode.MISS_MATCH_PW);
            }
        }
        feedCmtRepository.delete(feedCommentEntity);
    }

    //피드 리스트
    public FeedSimpleInfoVoList getFeedAll (Pageable pageable){
        Page<FeedEntity> feedEntities = feedRepository.findAllByCompleteAndFeedPrivateOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, Const.PUBLIC,pageable);

        return FeedSimpleInfoVoList.builder()
                .maxPage((long) feedEntities.getTotalPages())
                .hasNext(feedEntities.hasNext())
                .hasPrevious(feedEntities.hasPrevious())
                .feedSimpleInfoVoList(feedEntities.getContent().stream().map(
                        feedEntity -> {
                            String html = feedEntity.getContents();
                            Document doc = Jsoup.parse(html);
                            String contents = doc.body().text();
                            return FeedSimpleInfoVo.builder()
                                .feedPk(feedEntity.getFeedPk())
                                .feedTitle(feedEntity.getTitle())
                                .feedContent(contents)
                                .feedImg(feedEntity.getFeedPicsEntityList().isEmpty() ? null :
                                        feedEntity.getFeedPicsEntityList().get(0).getFeedPic())
                                .favCount(feedFavRepository.countByFeedEntity(feedEntity))
                                .cmtCount(feedCmtRepository.countByFeedEntity(feedEntity))
                                .createdAt(feedEntity.getCreatedAt().toString())
                                .hashTagList(hashtagRepository.findTop3ByFeedEntity(feedEntity).stream()
                                        .map(HashTagEntity::getHashTagNm).toList())
                                .blogPk(feedEntity.getBlogEntity().getBlogPk())
                                .blogTitle(feedEntity.getBlogEntity().getBlogTitle())
                                .blogImg(feedEntity.getBlogEntity().getBlogPic())
                                .build();}
                ).toList(
                )).build();

    }
    //구독한 블로그의 피드 리스트
    public FeedSimpleInfoVoList getFeedSub(Pageable pageable, UserEntity userEntity){
        List<BlogEntity> blogEntityList = blogSubRepository.findAllByUserEntity(userEntity).stream().map(BlogSubEntity::getBlogEntity).toList();
        Page<FeedEntity> feedEntities = feedRepository.findAllByCompleteAndFeedPrivateAndBlogEntityInOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, Const.PUBLIC, blogEntityList, pageable);
        return FeedSimpleInfoVoList.builder()
                .maxPage((long) feedEntities.getTotalPages())
                .hasNext(feedEntities.hasNext())
                .hasPrevious(feedEntities.hasPrevious())
                .feedSimpleInfoVoList(feedEntities.getContent().stream().map(
                        feedEntity -> {
                            String html = feedEntity.getContents();
                            Document doc = Jsoup.parse(html);
                            String contents = doc.body().text();
                            return FeedSimpleInfoVo.builder()
                                    .feedPk(feedEntity.getFeedPk())
                                    .feedTitle(feedEntity.getTitle())
                                    .feedContent(contents)
                                    .feedImg(feedEntity.getFeedPicsEntityList().isEmpty() ? null :
                                            feedEntity.getFeedPicsEntityList().get(0).getFeedPic())
                                    .favCount(feedFavRepository.countByFeedEntity(feedEntity))
                                    .cmtCount(feedCmtRepository.countByFeedEntity(feedEntity))
                                    .createdAt(feedEntity.getCreatedAt().toString())
                                    .hashTagList(hashtagRepository.findTop3ByFeedEntity(feedEntity).stream()
                                            .map(HashTagEntity::getHashTagNm).toList())
                                    .blogPk(feedEntity.getBlogEntity().getBlogPk())
                                    .blogTitle(feedEntity.getBlogEntity().getBlogTitle())
                                    .blogImg(feedEntity.getBlogEntity().getBlogPic())
                                    .build();}
                ).toList(
                )).build();


    }



//===============================================================================================================================
    private FeedEntity checkUserFeed(long feedPk, long blogPk){
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(FeedErrorCode.NOT_FOUND_FEED));
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
