package com.projcet.tstorycopyproject.domain.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatSubFeedInfoVo {
    private Long catPk;
    private String catName;
    private Long topSeq;
    private Long feedCount;
}
