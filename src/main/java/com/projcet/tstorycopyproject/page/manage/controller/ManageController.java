package com.projcet.tstorycopyproject.page.manage.controller;

import com.projcet.tstorycopyproject.global.CustomResponse;
import com.projcet.tstorycopyproject.global.auth.Login;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.page.manage.request.BlogModifyRq;
import com.projcet.tstorycopyproject.page.manage.request.CategoryInfoRq;
import com.projcet.tstorycopyproject.page.manage.response.CategoryInfoRp;
import com.projcet.tstorycopyproject.page.manage.service.ManageService;
import com.projcet.tstorycopyproject.page.member.response.BlogInfoRp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/{blogAddr}/manage")
public class ManageController {
    private final ManageService manageService;

    // 카테고리 관리
    @PutMapping("/category")
    @Operation(summary = "카테고리 수정 & 추가")
    public CustomResponse<List<CategoryInfoRp>> updateCategory(
            @PathVariable String blogAddr,
            @RequestBody List<CategoryInfoRq> catInfoList,
            @Login UserEntity user
    ) {
        return new CustomResponse<>(manageService.registerCategory(blogAddr,catInfoList,user));
    }


    // 블로그 정보 수정
    @PutMapping
    @Operation(summary = "블로그 정보 수정")
    public CustomResponse<BlogInfoRp> modifyBlogInfo(
            @PathVariable String blogAddr,
            @RequestBody BlogModifyRq blogModifyRq,
            @Login UserEntity user
    ) {

        return new CustomResponse<>(manageService.modifyBlog(blogAddr, blogModifyRq, user));
    }
}
