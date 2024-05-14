package com.projcet.tstorycopyproject.domain.feed.request;

import com.projcet.tstorycopyproject.domain.feed.response.HashTagInfoVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedInsDto {
    private Long feedPk;
    private Long blogPk;
    private Long catPk;
    private String title;
    private String content;
    private Long feedPrivate;
    private List<HashTagInfoVo> hashTagList;
}
