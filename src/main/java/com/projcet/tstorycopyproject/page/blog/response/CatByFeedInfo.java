package com.projcet.tstorycopyproject.page.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatByFeedInfo {
    private Long catPk;
    private String catName;
    private Long feedCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SubCatByPostsInfo> subCatByPostsInfoList;
}
