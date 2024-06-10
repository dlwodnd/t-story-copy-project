package com.projcet.tstorycopyproject.page.blog.service;

import com.projcet.tstorycopyproject.page.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.global.Const;
import com.projcet.tstorycopyproject.global.entity.*;
import com.projcet.tstorycopyproject.global.entity.embeddable.BlogSubComposite;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.*;
import com.projcet.tstorycopyproject.page.blog.request.FeedCmtInsRq;
import com.projcet.tstorycopyproject.page.blog.request.FeedCmtPutRq;
import com.projcet.tstorycopyproject.page.blog.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class BlogService {
    private final FeedRepository feedRepository;
    private final BlogRepository blogRepository;
    private final CatRepository catRepository;
    private final FeedFavRepository feedFavRepository;
    private final FeedCmtRepository feedCmtRepository;
    private final HashtagRepository hashtagRepository;
    private final BlogSubRepository blogSubRepository;

    // 카테고리 리스트
    public List<BlogCategoryRp> getCategoryList(String blogNm) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogNm).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        return blogEntity.getCategoryEntityList().stream()
                .map(category -> BlogCategoryRp.builder()
                        .catPk(category.getCatPk())
                        .catName(category.getCatNm())
                        .build())
                .toList();
    }
    // 카테고리별 포스트 갯수 조회 리스트
    public CatByFeedCountRp getCategory(long blogPk){
        BlogEntity blogEntity = blogRepository.findById(blogPk).orElseThrow(()->new CustomException(BlogErrorCode.NOT_FOUND_BLOG));

        List<CatByFeedInfo> catFeedInfoVoList = blogEntity.getCategoryEntityList().stream()
                .map(category -> {
                    long feedCount = feedRepository.countByCategoryEntity(category);
                    if (!category.getCategoryEntityList().isEmpty()){
                        feedCount = feedCount + category.getCategoryEntityList().stream()
                                .mapToLong(feedRepository::countByCategoryEntity)
                                .sum();
                    }
                    return CatByFeedInfo.builder()
                            .catPk(category.getCatPk())
                            .catName(category.getCatNm())
                            .feedCount(feedCount)
                            .subCatByPostsInfoList(category.getCategoryEntityList() == null || category.getCategoryEntityList().isEmpty() ? null :
                                    category.getCategoryEntityList().stream()
                                            .map(subCatEntity -> {
                                                long subFeedCount = feedRepository.countByCategoryEntity(subCatEntity);
                                                return SubCatByPostsInfo.builder()
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
        return CatByFeedCountRp.builder()
                .catAll("전체")
                .feedCount(feedRepository.countAllByBlogEntity(blogEntity))
                .catByFeedInfoList(catFeedInfoVoList)
                .build();
    }
    // 전체 포스트 리스트
    public FeedSimpleInfoListRp getFeedAll(String blogAddress, Pageable pageable) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddress).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        Page<FeedEntity> feedEntities = feedRepository.findAllByCompleteAndBlogEntityAndFeedPrivateOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, blogEntity, Const.PUBLIC, pageable);

        return FeedSimpleInfoListRp.builder()
                .maxPage((long) feedEntities.getTotalPages())
                .hasNext(feedEntities.hasNext())
                .hasPrevious(feedEntities.hasPrevious())
                .feedSimpleInfoList(feedEntities.getContent().stream().map(
                        feedEntity -> {
                            String html = feedEntity.getContents();
                            Document doc = Jsoup.parse(html);
                            String contents = doc.body().text().length() > 50 ? doc.body().text().substring(0, 50) + "..." : doc.body().text();
                            return FeedSimpleInfo.builder()
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
                                    .build();
                        }
                ).toList(
                )).build();

    }

    // 카테고리 별 포스트 리스트
    public FeedSimpleInfoListRp getFeedCategoryFilter(String blogAddress, String category, Pageable pageable) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddress).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        CategoryEntity categoryEntity = catRepository.findByCatNmAndBlogEntity(category, blogEntity).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_CATEGORY));
        Page<FeedEntity> feedEntities = feedRepository.findAllByCompleteAndBlogEntityAndFeedPrivateAndCategoryEntityOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, blogEntity, Const.PUBLIC, categoryEntity, pageable);

        return FeedSimpleInfoListRp.builder()
                .maxPage((long) feedEntities.getTotalPages())
                .hasNext(feedEntities.hasNext())
                .hasPrevious(feedEntities.hasPrevious())
                .feedSimpleInfoList(feedEntities.getContent().stream().map(
                        feedEntity -> {
                            String html = feedEntity.getContents();
                            Document doc = Jsoup.parse(html);
                            String contents = doc.body().text().length() > 50 ? doc.body().text().substring(0, 50) + "..." : doc.body().text();
                            return FeedSimpleInfo.builder()
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
                                    .build();
                        }
                ).toList(
                )).build();

    }

    // 포스트 상세 페이지
    public PostsDetailInfoRq getFeedDetail(String blogAddress, long feedPk) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddress).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        FeedEntity feedEntity = feedRepository.findById(feedPk).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_FEED));
        return PostsDetailInfoRq.builder()
                .feedId(feedEntity.getFeedPk())
                .title(feedEntity.getTitle())
                .content(feedEntity.getContents())
                .createdAt(feedEntity.getCreatedAt())
                .updatedAt(feedEntity.getUpdatedAt())
                .blogNm(blogEntity.getBlogNickname())
                .tags(feedEntity.getHashTagEntitySet().stream().map(
                                HashTagEntity::getHashTagNm
                        ).toList()
                ).build();
    }

    // 댓글 작성
    @Transactional
    public Void postFeedCmt(Long feedPk, FeedCmtInsRq rq, UserEntity userEntity) {
        FeedEntity feedEntity = feedRepository.findById(feedPk)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_FEED));
        FeedCommentEntity feedCommentEntity = FeedCommentEntity.builder()
                .feedEntity(feedEntity)
                .userEntity(userEntity)
                .cmt(rq.getCmt())
                .cmtPrivate(rq.getCmtPrivate())
                .build();
        feedCmtRepository.save(feedCommentEntity);
        return null;
    }

    // 댓글 삭제
    @Transactional
    public Void deleteFeedCmt(long cmtPk,UserEntity userEntity) {
        FeedCommentEntity feedCommentEntity = feedCmtRepository.findByCmtPkAndUserEntity(cmtPk,userEntity)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_FEED_CMT));
        feedCmtRepository.delete(feedCommentEntity);
        return null;
    }

    // 댓글 수정
    @Transactional
    public Void putFeedCmt(Long cmtPk, FeedCmtPutRq rq, UserEntity userEntity) {
        FeedCommentEntity feedCommentEntity = feedCmtRepository.findByCmtPkAndUserEntity(cmtPk,userEntity)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_FEED_CMT));
        feedCommentEntity.modifyFeedComment(rq);
        feedCmtRepository.save(feedCommentEntity);
        return null;
    }

    // 블로그 게시글 검색
    public FeedSimpleInfoVoList searchFeed(String blogAddress, String keyword, long searchType, Pageable pageable) {
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddress).orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        Page<FeedEntity> feedEntities = new PageImpl<>(List.of());
        if (searchType == 1) {
            feedEntities = feedRepository.findAllByCompleteAndBlogEntityAndFeedPrivateAndTitleContainingOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, blogEntity, Const.PUBLIC, keyword, pageable);
        }
        else if (searchType == 2) {
            feedEntities = feedRepository.findAllByCompleteAndBlogEntityAndFeedPrivateAndContentsContainingOrderByCreatedAtDesc(Const.FEED_POST_COMPLETE, blogEntity, Const.PUBLIC, keyword, pageable);
        }

        return feedEntities == null ? new FeedSimpleInfoVoList() : FeedSimpleInfoVoList.builder()
                .maxPage((long) feedEntities.getTotalPages())
                .hasNext(feedEntities.hasNext())
                .hasPrevious(feedEntities.hasPrevious())
                .feedSimpleInfoRpList(feedEntities.getContent().stream().map(
                        feedEntity -> {
                            String html = feedEntity.getContents();
                            Document doc = Jsoup.parse(html);
                            String contents = doc.body().text().length() > 50 ? doc.body().text().substring(0, 50) + "..." : doc.body().text();
                            return FeedSimpleInfoRp.builder()
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
                                    .build();
                        }
                ).toList(
                )).build();
    }

    //블로그 구독
    public Void subscribe(String blogAddr,UserEntity user){
        BlogEntity blogEntity = blogRepository.findByBlogAddress(blogAddr)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
        BlogSubComposite blogSubComposite = BlogSubComposite.builder()
                .blogPk(blogEntity.getBlogPk())
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
        return null;
    }
}
