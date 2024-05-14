package com.projcet.tstorycopyproject.domain.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CatSimpleVo {
    private Long catPk;
    private String catName;
}
