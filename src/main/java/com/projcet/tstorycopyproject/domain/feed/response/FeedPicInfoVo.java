package com.projcet.tstorycopyproject.domain.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedPicInfoVo {
    private Long feedPicPk;
    private String picName;
}
