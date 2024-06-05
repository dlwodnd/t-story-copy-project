package com.projcet.tstorycopyproject.page.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoRp {
    private Long blogPk;
    private String blogTitle;
    private String blogAddress;
    private String blogNickname;
    private String blogPic;
    private String blogInfo;

}
