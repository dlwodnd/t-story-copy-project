package com.projcet.tstorycopyproject.page.posts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TemporaryFeedRp {
    private Long feedPk;
    private String title;
    private String content;
    private FeedCategoryInfo categoryInfo;
    private List<HashTagInfoRp> hashTagInfoList;
}
