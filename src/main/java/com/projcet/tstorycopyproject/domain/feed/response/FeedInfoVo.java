package com.projcet.tstorycopyproject.domain.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedInfoVo {
    private Long feedPk;
    private Long catPk;
    private String catName;
    private String title;
    private String contents;
    private List<HashTagInfoVo> hashTagList;
}
