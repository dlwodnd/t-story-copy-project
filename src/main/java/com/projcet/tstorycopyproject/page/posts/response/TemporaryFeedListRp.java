package com.projcet.tstorycopyproject.page.posts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryFeedListRp {
    private List<TemporaryFeed> temporaryFeedList;
    private int totalPage;
}
