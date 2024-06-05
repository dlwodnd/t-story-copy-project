package com.projcet.tstorycopyproject.page.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedSimpleInfoListRp {
    private Long maxPage;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private List<FeedSimpleInfo> feedSimpleInfoList;
}
