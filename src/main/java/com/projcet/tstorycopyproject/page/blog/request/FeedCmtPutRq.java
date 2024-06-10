package com.projcet.tstorycopyproject.page.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedCmtPutRq {
    private String cmt;
    private Long cmtPrivate;
}
