package com.projcet.tstorycopyproject.page.manage.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogModifyRq {
    private String blogTitle;
    private String blogInfo;
    private String blogNickname;
}
