package com.projcet.tstorycopyproject.page.manage.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRegisterRq {
    private String blogTitle;
    private String blogAddress;
    private String blogNickname;
}
