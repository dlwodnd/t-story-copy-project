package com.projcet.tstorycopyproject.domain.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorySubInfoRp {
    private Long catPk;
    private Long catSeq;
    private String catName;
    private Long topSeq;
    private Long catOrder;
}
