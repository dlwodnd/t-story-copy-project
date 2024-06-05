package com.projcet.tstorycopyproject.page.posts.controller;

import com.projcet.tstorycopyproject.page.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {
    private final PostsService postsService;
}
