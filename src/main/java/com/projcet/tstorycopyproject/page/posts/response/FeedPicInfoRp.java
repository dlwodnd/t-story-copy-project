package com.projcet.tstorycopyproject.page.posts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedPicInfoRp {
    private Long feedPicPk;
    private String picName;
}
