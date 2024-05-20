package com.projcet.tstorycopyproject.domain.feed.controller;

import com.projcet.tstorycopyproject.domain.feed.request.FeedCmtInsDto;
import com.projcet.tstorycopyproject.domain.feed.request.FeedCmtPutDto;
import com.projcet.tstorycopyproject.domain.feed.request.FeedInsDto;
import com.projcet.tstorycopyproject.domain.feed.response.CatSimpleVo;
import com.projcet.tstorycopyproject.domain.feed.response.FeedPicInfoVo;
import com.projcet.tstorycopyproject.domain.feed.response.FeedSimpleInfoVoList;
import com.projcet.tstorycopyproject.domain.feed.service.FeedService;
import com.projcet.tstorycopyproject.global.CustomResponse;
import com.projcet.tstorycopyproject.global.auth.Login;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;

    //카테고리 이름 pk 불러오기
    @GetMapping("/category-list")
    public ResponseEntity<CustomResponse<List<CatSimpleVo>>> getCategoryList(
            @RequestParam Long blogPk
            , @Login UserEntity userEntity) {
        return ResponseEntity.ok(new CustomResponse<>(feedService.getCategoryList(blogPk, userEntity)));
    }


    //글 작성 버튼을 누르면 전에 작성한 피드가 있으면 불러오고 없으면 빈 피드를 불러온다.
    @GetMapping("/empty")
    public ResponseEntity<CustomResponse<Void>> getEmptyFeed(
            @RequestParam Long blogPk
            , @Login UserEntity userEntity) {
        feedService.getEmptyFeed(blogPk, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 삭제
    @DeleteMapping("/{blogPk}")
    public ResponseEntity<CustomResponse<Void>> deleteFeed(
            @RequestParam Long feedPk
            , @PathVariable Long blogPk
            , @Login UserEntity userEntity) {
        feedService.deleteFeed(feedPk, blogPk, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 사진 등록
    @PostMapping("/pic")
    public ResponseEntity<CustomResponse<FeedPicInfoVo>> postFeedPic(
            @RequestParam Long feedPk
            , @RequestPart MultipartFile pic
            , @Login UserEntity userEntity) {
        feedService.postFeedPic(feedPk, pic,userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 사진 삭제
    @DeleteMapping("/pic")
    public ResponseEntity<CustomResponse<Void>> deleteFeedPic(
            @RequestParam Long feedPicPk
            , @Login UserEntity userEntity) {
        feedService.deleteFeedPic(feedPicPk,userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 수정
    @PutMapping
    public ResponseEntity<CustomResponse<Void>> putFeed(
            @RequestBody FeedInsDto dto
            , @Login UserEntity userEntity) {
        feedService.putFeed(dto, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 좋아요
    @PatchMapping("/fav")
    public ResponseEntity<CustomResponse<Void>> patchFeedFav(@RequestParam Long feedPk
            , @Login UserEntity userEntity) {
        feedService.patchFeedFav(feedPk, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 댓글 등록
    @PostMapping("/cmt")
    public ResponseEntity<CustomResponse<Void>> postFeedCmt(@RequestBody FeedCmtInsDto dto
            , @Login UserEntity userEntity) {
        feedService.postFeedCmt(dto, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 댓글 수정
    @PutMapping("/cmt")
    public ResponseEntity<CustomResponse<Void>> putFeedCmt(
            @RequestBody FeedCmtPutDto dto
            , @Login UserEntity userEntity) {
        feedService.putFeedCmt(dto, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //피드 댓글 삭제
    @DeleteMapping("/cmt")
    public ResponseEntity<CustomResponse<Void>> deleteFeedCmt(
            @RequestParam Long feedCmtPk
            , @RequestBody String guestPw
            , @Login UserEntity userEntity) {
        feedService.deleteFeedCmt(feedCmtPk, guestPw, userEntity);
        return ResponseEntity.ok(new CustomResponse<>());
    }

    //전체 피드 리스트 출력
    @GetMapping("/main")
    public ResponseEntity<CustomResponse<FeedSimpleInfoVoList>> getFeedList(
            @RequestParam Pageable pageable) {
        return ResponseEntity.ok(new CustomResponse<>(feedService.getFeedAll(pageable)));
    }

    //구독한 블로그 피드 리스트
    @GetMapping("/sub")
    public ResponseEntity<CustomResponse<FeedSimpleInfoVoList>> getFeedSubList(
            @RequestParam Pageable pageable
            , @Login UserEntity userEntity) {
        return ResponseEntity.ok(new CustomResponse<>(feedService.getFeedSub(pageable, userEntity)));
    }
}
