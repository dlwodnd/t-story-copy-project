package com.projcet.tstorycopyproject.domain.blog.controller;

import com.projcet.tstorycopyproject.domain.blog.request.BlogModifyRq;
import com.projcet.tstorycopyproject.domain.blog.request.BlogRegisterRq;
import com.projcet.tstorycopyproject.domain.blog.request.CategoryInsRq;
import com.projcet.tstorycopyproject.domain.blog.response.BlogInfoRp;
import com.projcet.tstorycopyproject.domain.blog.response.CategoryInfoRp;
import com.projcet.tstorycopyproject.domain.blog.service.BlogService;
import com.projcet.tstorycopyproject.global.CustomResponse;
import com.projcet.tstorycopyproject.global.auth.Login;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogService blogService;

    //블로그 등록
    @PostMapping
    public ResponseEntity<CustomResponse<BlogInfoRp>> registerBlog(
            @RequestPart @Valid BlogRegisterRq dto
            , @RequestPart MultipartFile blogThumbnail
            , @Login UserEntity user) {
        return ResponseEntity.ok(new CustomResponse<>(blogService.registerBlog(dto, blogThumbnail, user)));
    }

    //블로그 정보 수정
    @PutMapping
    public ResponseEntity<CustomResponse<BlogInfoRp>> modifyBlog(
            @RequestPart @Valid BlogModifyRq dto
            , @Login UserEntity user) {
        return ResponseEntity.ok(new CustomResponse<>(blogService.modifyBlog(dto, user)));
    }

    //블로그 삭제
    @DeleteMapping()
    public ResponseEntity<CustomResponse<String>> closeBlog(
            @RequestParam long blogPk
            , @Login UserEntity user) {
        blogService.closeBlog(blogPk, user);
        return ResponseEntity.ok(new CustomResponse<>("블로그 삭제 성공"));
    }

    //카테고리 등록
    @PutMapping("/category")
    public ResponseEntity<CustomResponse<List<CategoryInfoRp>>> registerCategory(
            @RequestBody CategoryInsRq dto
            , @Login UserEntity user) {
        long start = System.currentTimeMillis();
        List<CategoryInfoRp> vo = blogService.registerCategory(dto, user);
        long end = System.currentTimeMillis();
        System.out.println("실행 시간 : " + (end - start) / 1000.0);
        return ResponseEntity.ok(new CustomResponse<>(vo));
    }

    //댓글 허용 설정 토글
    @PatchMapping("/cmtAccess")
    public ResponseEntity<CustomResponse<Void>> cmtAccess(
            @RequestParam long blogPk
            , @Login UserEntity user) {
        return ResponseEntity.ok(new CustomResponse<>(blogService.cmtAccess(blogPk, user)));
    }

    //블로그 구독
    @PostMapping("/subscribe")
    public ResponseEntity<CustomResponse<Void>> subscribe(
            @RequestParam long blogPk
            , @Login UserEntity user) {
        blogService.subscribe(blogPk, user);
        return ResponseEntity.ok(new CustomResponse<>());
    }
}
