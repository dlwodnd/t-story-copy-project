package com.projcet.tstorycopyproject.domain.feed.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedCmtPutDto {
    private Long cmtPk;
    private String cmtPw;
    private String cmt;
    private Long cmtPrivate;
}
