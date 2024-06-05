package com.projcet.tstorycopyproject.page.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogCategoryRp {
    private Long catPk;
    private String catName;
}
