package com.projcet.tstorycopyproject.page.blog.controller;


import com.projcet.tstorycopyproject.global.CustomResponse;
import com.projcet.tstorycopyproject.global.auth.Login;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.page.blog.request.FeedCmtInsRq;
import com.projcet.tstorycopyproject.page.blog.request.FeedCmtPutRq;
import com.projcet.tstorycopyproject.page.blog.response.BlogCategoryRp;
import com.projcet.tstorycopyproject.page.blog.response.FeedSimpleInfoListRp;
import com.projcet.tstorycopyproject.page.blog.response.FeedSimpleInfoVoList;
import com.projcet.tstorycopyproject.page.blog.response.PostsDetailInfoRq;
import com.projcet.tstorycopyproject.page.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{blogAddr}")
public class BlogController {
    private final BlogService blogService;
    // 카테고리 리스트
    @GetMapping("/category")
    @Operation(summary = "게시글 작성에 사용되는 카테고리 리스트")
    public CustomResponse<List<BlogCategoryRp>> getBlogCategoryList(
            @PathVariable String blogAddr
    ) {
        return new CustomResponse<>(blogService.getCategoryList(blogAddr));
    }

    // 전체 포스트 리스트
    @GetMapping
    @Operation(summary = "전체 게시글 리스트")
    public CustomResponse<FeedSimpleInfoListRp> getFeedSimpleInfoList(
            @PathVariable String blogAddr,
            @RequestParam(defaultValue = "1") int page
    ) {
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = getPage(page);
        return new CustomResponse<>(blogService.getFeedAll(blogAddr,pageable));
    }

    // 카테고리 별 포스트 리스트
    @GetMapping
    @Operation(summary = "카테고리 게시글 리스트")
    public CustomResponse<FeedSimpleInfoListRp> getCategoryByPosts(
            @PathVariable String blogAddr,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam String categoryNm
    ) {
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = getPage(page);
        return new CustomResponse<>(blogService.getFeedCategoryFilter(blogAddr,categoryNm,pageable));
    }



    // 포스트 상세 페이지
    @GetMapping("/{postPk}")
    @Operation(summary = "게시글 상세 정보")
    public CustomResponse<PostsDetailInfoRq> getFeedDetail(
            @PathVariable String blogAddr,
            @PathVariable Long postPk
    ) {
        return new CustomResponse<>(blogService.getFeedDetail(blogAddr,postPk));
    }

    // 댓글 작성
    @PostMapping("/{postPk}/comment")
    @Operation(summary = "댓글 작성")
    public CustomResponse<Void> postComment(
            @PathVariable String blogAddr,
            @PathVariable Long postPk,
            @RequestBody FeedCmtInsRq rq,
            @Login UserEntity userEntity
    ) {
        return new CustomResponse<>(blogService.postFeedCmt(postPk,rq,userEntity));
    }

    // 댓글 삭제
    @DeleteMapping("/{postPk}/comment")
    @Operation(summary = "댓글 삭제")
    public CustomResponse<Void> deleteComment(
            @PathVariable String blogAddr,
            @PathVariable Long postPk,
            @RequestParam Long cmtPk,
            @Login UserEntity userEntity
    ) {
        return new CustomResponse<>(blogService.deleteFeedCmt(cmtPk,userEntity));
    }

    // 댓글 수정
    @PutMapping("/{postPk}/comment")
    @Operation(summary = "댓글 수정")
    public CustomResponse<Void> putComment(
            @RequestParam Long cmtPk,
            @RequestBody FeedCmtPutRq rq,
            @Login UserEntity userEntity
    ) {
        return new CustomResponse<>(blogService.putFeedCmt(cmtPk,rq,userEntity));
    }
    // 블로그 게시글 검색
    @GetMapping("/search/{search}")
    public CustomResponse<FeedSimpleInfoVoList> searchFeed(
            @PathVariable String blogAddr,
            @PathVariable String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") long searchType
    ) {
        if (page < 1) {
            page = 1;
        }
        if (searchType < 1 || searchType > 2) {
            searchType = 1;
        }
        Pageable pageable = getPage(page);
        return new CustomResponse<>(blogService.searchFeed(blogAddr,search,searchType,pageable));
    }

    // 블로그 구독
    @PostMapping("/subscribe")
    @Operation(summary = "블로그 구독")
    public CustomResponse<Void> subscribeBlog(
            @PathVariable String blogAddr,
            @Login UserEntity userEntity
    ) {
        return new CustomResponse<>(blogService.subscribe(blogAddr,userEntity));
    }




    // Pageable 객체 생성
    private Pageable getPage(int page){
        if (page < 1) {
            page = 1;
        }
        return PageRequest.of(page-1, 10);
    }
}
