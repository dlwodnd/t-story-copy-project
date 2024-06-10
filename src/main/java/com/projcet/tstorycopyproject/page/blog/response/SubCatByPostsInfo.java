package com.projcet.tstorycopyproject.page.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SubCatByPostsInfo {
    private Long catPk;
    private String catName;
    private Long topSeq;
    private Long feedCount;
}
