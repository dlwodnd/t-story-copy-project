package com.projcet.tstorycopyproject.page.blog.controller;


import com.projcet.tstorycopyproject.page.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{blogNm}")
public class BlogController {
    private final BlogService blogService;
    // 카테고리 리스트

    // 전체 포스트 리스트

    // 카테고리 별 포스트 리스트

    // 포스트 상세 페이지

    // 댓글 작성

    // 댓글 삭제

    // 댓글 수정
}
