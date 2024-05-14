package com.projcet.tstorycopyproject.domain.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CatVo {
    private Long feedCount;
    private String catAll;
    private List<CatFeedInfoVo> catFeedInfoVoList;
}
