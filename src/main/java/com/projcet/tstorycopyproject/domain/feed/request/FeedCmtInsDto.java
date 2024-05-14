package com.projcet.tstorycopyproject.domain.feed.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedCmtInsDto {
    private Long feedPk;
    private String cmtNm;
    private String cmtPw;
    private String cmt;
    private Long cmtPrivate;
}
