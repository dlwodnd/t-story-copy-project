package com.projcet.tstorycopyproject.page.posts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedCategoryInfo {
    private Long categoryPk;
    private String categoryName;
}
