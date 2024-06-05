package com.projcet.tstorycopyproject.page.posts.request;

import com.projcet.tstorycopyproject.page.posts.response.HashTagInfoRp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedInsRq {
    private Long feedPk;
    private Long blogPk;
    private Long catPk;
    private String title;
    private String content;
    private Long feedPrivate;
    private List<HashTagInfoRp> hashTagList;
}
