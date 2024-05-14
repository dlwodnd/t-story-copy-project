package com.projcet.tstorycopyproject.domain.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogModifyRq {
    private Long blogPk;
    private String blogTitle;
    private String blogInfo;
    private String blogNickname;
}
