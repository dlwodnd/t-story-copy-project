package com.projcet.tstorycopyproject.page.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedSimpleInfoRp {
    private Long feedPk;
    private String feedTitle;
    private String feedContent;
    private String feedImg;
    private Long favCount;
    private Long cmtCount;
    private String createdAt;
    private List<String> hashTagList;

    private Long blogPk;
    private String blogTitle;
    private String blogImg;

}
