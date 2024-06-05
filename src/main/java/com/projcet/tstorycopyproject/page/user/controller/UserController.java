package com.projcet.tstorycopyproject.page.user.controller;

import com.projcet.tstorycopyproject.page.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    // 유저 정보

    // 유저 로그인

    // 유저 로그아웃

    // 리프레쉬 토큰 재발급
}
