package com.projcet.tstorycopyproject.domain.catagory.controller;

import com.projcet.tstorycopyproject.domain.catagory.service.CategoryService;
import com.projcet.tstorycopyproject.domain.feed.response.CatVo;
import com.projcet.tstorycopyproject.global.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    //카테고리 리스트 불러오기
    @GetMapping
    public ResponseEntity<CustomResponse<CatVo>> getCategory(@RequestParam Long blogPk) {
        return ResponseEntity.ok(new CustomResponse<>(categoryService.getCategory(blogPk)));
    }
}
