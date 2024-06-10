package com.projcet.tstorycopyproject.page.blog.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatByFeedCountRp {
    private Long feedCount;
    private String catAll;
    private List<CatByFeedInfo> catByFeedInfoList;
}
