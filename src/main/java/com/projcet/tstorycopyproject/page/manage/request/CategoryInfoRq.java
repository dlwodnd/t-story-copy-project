package com.projcet.tstorycopyproject.page.manage.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoRq {
    private Long catPk;
    private Long catSeq;
    private String catName;
    private Long catOrder;
    private Long topCatSeq;
}
